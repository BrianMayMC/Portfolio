package me.nootnoot.framework.storagesystem;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* *
 * Created by Joshua Bell (RingOfStorms)
 *
 * Post explaining here: [URL]http://bukkit.org/threads/gsonfactory-gson-that-works-on-itemstack-potioneffect-location-objects.331161/[/URL]
 * */
public class GsonFactory {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public static @interface Ignore {
	}

    /*
    - I want to not use Bukkit parsing for most objects... it's kind of clunky
    - Instead... I want to start using any of Mojang's tags
    - They're really well documented + built into MC, and handled by them.
    - Rather than kill your old code, I'm going to write TypeAdapaters using Mojang's stuff.
     */

	private static Gson g = new Gson();

	private final static String CLASS_KEY = "SERIAL-ADAPTER-CLASS-KEY";

	private static Gson prettyGson;
	private static Gson compactGson;

	/**
	 * Returns a Gson instance for use anywhere with new line pretty printing
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 *
	 * @return a Gson instance
	 */
	public static Gson getPrettyGson() {
		if (prettyGson == null)
			prettyGson = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
					.addDeserializationExclusionStrategy(new ExposeExlusion())
					.registerTypeAdapter(Location.class, new LocationGsonAdapter())
					.registerTypeAdapter(Date.class, new DateGsonAdapter())
					.setPrettyPrinting()
					.disableHtmlEscaping()
					.create();
		return prettyGson;
	}

	/**
	 * Returns a Gson instance for use anywhere with one line strings
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 *
	 * @return a Gson instance
	 */
	public static Gson getCompactGson() {
		if (compactGson == null)
			compactGson = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
					.addDeserializationExclusionStrategy(new ExposeExlusion())
					.registerTypeAdapter(Location.class, new LocationGsonAdapter())
					.registerTypeAdapter(Date.class, new DateGsonAdapter())
					.disableHtmlEscaping()
					.create();
		return compactGson;
	}

	/**
	 * Creates a new instance of Gson for use anywhere
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 *
	 * @return a Gson instance
	 */
	public static Gson getNewGson(boolean prettyPrinting) {
		GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
				.addDeserializationExclusionStrategy(new ExposeExlusion())
				.disableHtmlEscaping();
		if (prettyPrinting)
			builder.setPrettyPrinting();
		return builder.create();
	}

	private static Map<String, Object> recursiveSerialization(ConfigurationSerializable o) {
		Map<String, Object> originalMap = o.serialize();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : originalMap.entrySet()) {
			Object o2 = entry.getValue();
			if (o2 instanceof ConfigurationSerializable) {
				ConfigurationSerializable serializable = (ConfigurationSerializable) o2;
				Map<String, Object> newMap = recursiveSerialization(serializable);
				newMap.put(CLASS_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
				map.put(entry.getKey(), newMap);
			}
		}
		map.put(CLASS_KEY, ConfigurationSerialization.getAlias(o.getClass()));
		return map;
	}

	private static Map<String, Object> recursiveDoubleToInteger(Map<String, Object> originalMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : originalMap.entrySet()) {
			Object o = entry.getValue();
			if (o instanceof Double) {
				Double d = (Double) o;
				Integer i = d.intValue();
				map.put(entry.getKey(), i);
			} else if (o instanceof Map) {
				Map<String, Object> subMap = (Map<String, Object>) o;
				map.put(entry.getKey(), recursiveDoubleToInteger(subMap));
			} else {
				map.put(entry.getKey(), o);
			}
		}
		return map;
	}

	private static class ExposeExlusion implements ExclusionStrategy {
		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttributes) {
			final Ignore ignore = fieldAttributes.getAnnotation(Ignore.class);
			if (ignore != null)
				return true;
			final Expose expose = fieldAttributes.getAnnotation(Expose.class);
			return expose != null && (!expose.serialize() || !expose.deserialize());
		}

		@Override
		public boolean shouldSkipClass(Class<?> aClass) {
			return false;
		}
	}


	private static class LocationGsonAdapter extends TypeAdapter<Location> {

		private static Type seriType = new TypeToken<Map<String, Object>>() {
		}.getType();

		private static String UUID = "uuid";
		private static String X = "x";
		private static String Y = "y";
		private static String Z = "z";
		private static String YAW = "yaw";
		private static String PITCH = "pitch";

		@Override
		public void write(JsonWriter jsonWriter, Location location) throws IOException {
			if (location == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(getRaw(location));
		}

		@Override
		public Location read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return fromRaw(jsonReader.nextString());
		}

		private String getRaw(Location location) {
			Map<String, Object> serial = new HashMap<String, Object>();
			serial.put(UUID, location.getWorld().getUID().toString());
			serial.put(X, Double.toString(location.getX()));
			serial.put(Y, Double.toString(location.getY()));
			serial.put(Z, Double.toString(location.getZ()));
			serial.put(YAW, Float.toString(location.getYaw()));
			serial.put(PITCH, Float.toString(location.getPitch()));
			return g.toJson(serial);
		}

		private Location fromRaw(String raw) {
			Map<String, Object> keys = g.fromJson(raw, seriType);
			World w = Bukkit.getWorld(java.util.UUID.fromString((String) keys.get(UUID)));
			return new Location(w, Double.parseDouble((String) keys.get(X)), Double.parseDouble((String) keys.get(Y)), Double.parseDouble((String) keys.get(Z)),
					Float.parseFloat((String) keys.get(YAW)), Float.parseFloat((String) keys.get(PITCH)));
		}
	}


	private static class DateGsonAdapter extends TypeAdapter<Date> {
		@Override
		public void write(JsonWriter jsonWriter, Date date) throws IOException {
			if (date == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(date.getTime());
		}

		@Override
		public Date read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return new Date(jsonReader.nextLong());
		}
	}
}

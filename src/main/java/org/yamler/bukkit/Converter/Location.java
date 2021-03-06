package org.yamler.bukkit.Converter;

import org.bukkit.Bukkit;
import org.yamler.yamler.ConfigSection;
import org.yamler.yamler.Converter.Converter;
import org.yamler.yamler.GenericData;
import org.yamler.yamler.InternalConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Location implements Converter
{
	public Location(InternalConverter converter)
	{
	}

	@Override
	public Object toConfig(Class<?> type, Object obj) throws Exception
	{
		org.bukkit.Location location = (org.bukkit.Location) obj;
		Map<String, Object> saveMap = new HashMap<>();
		saveMap.put("world", location.getWorld().getName());
		saveMap.put("x", location.getX());
		saveMap.put("y", location.getY());
		saveMap.put("z", location.getZ());
		saveMap.put("yaw", location.getYaw());
		saveMap.put("pitch", location.getPitch());

		return saveMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object fromConfig(Class type, Object section, GenericData genericData) throws Exception
	{
		Map<String, Object> locationMap;
		if(section instanceof Map)
			locationMap = (Map<String, Object>) section;
		else
			locationMap = (Map<String, Object>) ((ConfigSection) section).getRawMap();

		Float yaw;
		if(locationMap.get("yaw") instanceof Double)
		{
			Double dYaw = (Double) locationMap.get("yaw");
			yaw = dYaw.floatValue();
		} else
			yaw = (Float) locationMap.get("yaw");

		Float pitch;
		if(locationMap.get("pitch") instanceof Double)
		{
			Double dPitch = (Double) locationMap.get("pitch");
			pitch = dPitch.floatValue();
		} else
			pitch = (Float) locationMap.get("pitch");

		return new org.bukkit.Location(Bukkit.getWorld((String) locationMap.get("world")),
				(Double) locationMap.get("x"),
				(Double) locationMap.get("y"),
				(Double) locationMap.get("z"),
				yaw,
				pitch
		);
	}

	@Override
	public boolean supports(Class<?> type)
	{
		return org.bukkit.Location.class.isAssignableFrom(type);
	}

}

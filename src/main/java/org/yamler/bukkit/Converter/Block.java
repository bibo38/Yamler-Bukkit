package org.yamler.bukkit.Converter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.yamler.yamler.ConfigSection;
import org.yamler.yamler.Converter.Converter;
import org.yamler.yamler.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Block implements Converter {
    private InternalConverter converter;

    public Block(InternalConverter converter) {
        this.converter = converter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
        org.bukkit.block.Block block = (org.bukkit.block.Block) obj;

        Converter locationConverter = converter.getConverter(org.bukkit.Location.class);
        Map<String, Object> saveMap = new HashMap<>();
        saveMap.put("id", block.getType() + ((block.getData() > 0) ? ":" + block.getData() : ""));
        saveMap.put("location", locationConverter.toConfig(org.bukkit.Location.class, block.getLocation(), null));

        return saveMap;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        Map<String, Object> blockMap = (Map<String, Object>) ((ConfigSection) section).getRawMap();
        Map<String, Object> locationMap = (Map<String, Object>) ((ConfigSection) blockMap.get("location")).getRawMap();

        Location location = new org.bukkit.Location(Bukkit.getWorld((String) locationMap.get("world")), (Double) locationMap.get("x"), (Double) locationMap.get("y"), (Double) locationMap.get("z"));
        org.bukkit.block.Block block = location.getBlock();

        String[] temp = ((String) blockMap.get("id")).split(":");
        block.setType(Material.valueOf(temp[0]));

        if (temp.length == 2) {
            block.setData(Byte.valueOf(temp[1]));
        }

        return block;
    }

    @Override
    public boolean supports(Class<?> type) {
        return org.bukkit.block.Block.class.isAssignableFrom(type);
    }

}

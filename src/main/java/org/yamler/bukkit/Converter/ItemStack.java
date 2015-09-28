package org.yamler.bukkit.Converter;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.yamler.yamler.ConfigSection;
import org.yamler.yamler.Converter.Converter;
import org.yamler.yamler.GenericData;
import org.yamler.yamler.InternalConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @author bibo38
 */
public class ItemStack implements Converter
{
	private InternalConverter converter;

	public ItemStack(InternalConverter converter)
	{
		this.converter = converter;
	}

	@Override
	public Object toConfig(Class<?> type, Object obj) throws Exception
	{
		org.bukkit.inventory.ItemStack itemStack = (org.bukkit.inventory.ItemStack) obj;

		Map<String, Object> saveMap = new HashMap<>();
		saveMap.put("id", itemStack.getType() + ((itemStack.getDurability() > 0) ? ":" + itemStack.getDurability() : ""));
		saveMap.put("amount", itemStack.getAmount());

		Converter listConverter = converter.getConverter(List.class);

		Map<String, Object> meta = null;
		if(itemStack.hasItemMeta())
		{
			meta = new HashMap<>();
			ItemMeta itemMeta = itemStack.getItemMeta();

			meta.put("name", itemMeta.hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : null);
			meta.put("lore", itemMeta.hasLore() ? listConverter.toConfig(List.class, itemMeta.getLore()) : null);
			if(itemMeta instanceof LeatherArmorMeta)
				meta.put("color", Integer.toHexString(
						((LeatherArmorMeta) itemMeta)
							.getColor()
							.asRGB()
					).toUpperCase()
				);
		}

		saveMap.put("meta", meta);

		return saveMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object fromConfig(Class type, Object section, GenericData genericData) throws Exception
	{
		Map itemstackMap;
		Map metaMap = null;

		if(section instanceof Map)
		{
			itemstackMap = (Map) section;
			metaMap = (Map) itemstackMap.get("meta");
		} else
		{
			itemstackMap = ((ConfigSection) section).getRawMap();
			if(itemstackMap.get("meta") != null)
				metaMap = ((ConfigSection) itemstackMap.get("meta")).getRawMap();
		}

		String[] temp = ((String) itemstackMap.get("id")).split(":");
		org.bukkit.inventory.ItemStack itemStack = (org.bukkit.inventory.ItemStack)
				type.getConstructor(Material.class).newInstance(Material.valueOf(temp[0]));
		itemStack.setAmount((int) itemstackMap.get("amount"));

		if(temp.length == 2)
			itemStack.setDurability(Short.valueOf(temp[1]));


		if(metaMap != null)
		{
			ItemMeta meta = itemStack.getItemMeta();

			if(metaMap.get("name") != null)
				meta.setDisplayName((String) metaMap.get("name"));


			if(metaMap.get("lore") != null)
			{
				Converter listConverter = converter.getConverter(List.class);
				meta.setLore((List<String>) listConverter.fromConfig(List.class, metaMap.get("lore"), genericData));
			}

			if(meta instanceof LeatherArmorMeta)
				((LeatherArmorMeta) meta).setColor(Color.fromRGB(
						Integer.parseInt(
								(String) metaMap.get("color"),
								16
						)
				));

			itemStack.setItemMeta(meta);
		}

		return itemStack;
	}

	@Override
	public boolean supports(Class<?> type)
	{
		return org.bukkit.inventory.ItemStack.class.isAssignableFrom(type);
	}

}

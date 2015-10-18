package org.yamler.bukkit.tests.converter.config;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.yamler.bukkit.Converter.ItemStack;
import org.yamler.bukkit.tests.helper.TestItemStack;
import org.yamler.yamler.Config;

import java.util.*;

/**
 * The test configuration to check for a correct working ItemStack.
 *
 * @author bibo38
 * @see org.yamler.bukkit.tests.converter.ItemStackConverterTest
 */
public class ItemStackTestConfig extends Config
{

	public TestItemStack cookie = new TestItemStack(Material.COOKIE);
	public TestItemStack specialWool = new TestItemStack(Material.WOOL);

	/**
	 * The default constructor is used to add the {@link ItemStack} converter
	 * and add a special ItemMeta class to the {@link this#specialWool} variable to test the converter.
	 *
	 * @throws Exception When the converter cannot be added
	 */
	public ItemStackTestConfig() throws Exception
	{
		addConverter(ItemStack.class);

		specialWool.setItemMeta(new ItemMeta()
		{
			@Override
			public boolean hasDisplayName()
			{
				return true;
			}

			@Override
			public String getDisplayName()
			{
				return "Test Wool";
			}

			@Override
			public void setDisplayName(String s)
			{
			}

			@Override
			public boolean hasLore()
			{
				return true;
			}

			@Override
			public List<String> getLore()
			{
				return Arrays.asList("This is Wool", "Good Wool");
			}

			@Override
			public void setLore(List<String> list)
			{
			}

			@Override
			public boolean hasEnchants()
			{
				return false;
			}

			@Override
			public boolean hasEnchant(Enchantment enchantment)
			{
				return Enchantment.LUCK.equals(enchantment);
			}

			@Override
			public int getEnchantLevel(Enchantment enchantment)
			{
				return hasEnchant(enchantment) ? 1 : 0;
			}

			@Override
			public Map<Enchantment, Integer> getEnchants()
			{
				HashMap<Enchantment, Integer> enchantments = new HashMap<>();
				enchantments.put(Enchantment.LUCK, 1);
				return enchantments;
			}

			@Override
			public boolean addEnchant(Enchantment enchantment, int i, boolean b)
			{
				return false;
			}

			@Override
			public boolean removeEnchant(Enchantment enchantment)
			{
				return false;
			}

			@Override
			public boolean hasConflictingEnchant(Enchantment enchantment)
			{
				return false;
			}

			@SuppressWarnings("CloneDoesntCallSuperClone")
			@Override
			public ItemMeta clone()
			{
				return null;
			}

			@Override
			public Map<String, Object> serialize()
			{
				return null;
			}

			@Override
			public void addItemFlags(ItemFlag... itemFlags)
			{
			}

			@Override
			public void removeItemFlags(ItemFlag... itemFlags)
			{
			}

			@Override
			public Set<ItemFlag> getItemFlags()
			{
				return new HashSet<>();
			}

			@Override
			public boolean hasItemFlag(ItemFlag flag)
			{
				return false;
			}
		});
	}
}

package firis.tatara.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentMobSpawner extends Enchantment {

	public EnchantmentMobSpawner() {
		
		//int enchID, ResourceLocation enchName, int enchWeight,EnumEnchantmentType enchType
		super(201, new ResourceLocation("mob_spawner"), 1, EnumEnchantmentType.DIGGER);
		
		this.setName("mobSpawner");
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
	@Override
    public int getMinEnchantability(int enchantmentLevel)
    {
		//return 1 + enchantmentLevel * 10;
		return 1;
    }

	/** エンチャントのしやすさを補正値込みで判定してるっぽい？ */
    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
	@Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        //return this.getMinEnchantability(enchantmentLevel) + 20;
        return 30;
    }
    
    
	/**
     * Returns the maximum level that the enchantment can have.
     */
	@Override
    public int getMaxLevel()
    {
        return 1;
    }
}

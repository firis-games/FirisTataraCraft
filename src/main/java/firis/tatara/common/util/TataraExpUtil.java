package firis.tatara.common.util;

import net.minecraft.entity.player.EntityPlayer;

/**
 * 経験値関連のUtilを取り扱う
 *
 */
public class TataraExpUtil {

	/**
	 * レベル分の経験値ポイントを減算する
	 */
	public static boolean subtractionPlayerLevel(EntityPlayer playerIn, int level) {
		
		boolean result = false;
		
		//総経験値取得ポイントを退避
		int svExperienceTotal = playerIn.experienceTotal;
		
		//指定レベルの経験値ポイントを取得
		int experience = getExperienceFromLevel(level);
		
		//Floatのせいでどうしても計算がずれる
		//とりあえず30と50のときにずれない四捨五入でやる
		int playerExperience = getExperienceFromLevel(playerIn.experienceLevel);
		playerExperience += Math.round((playerIn.experience * (float)playerIn.xpBarCap()));
		
		if (experience <= playerExperience) {
			
			//プレイヤーの経験値ポイントが大きい場合に減算を行う
			playerExperience = playerExperience - experience;
			
			//プレイヤーレベルの初期化
			//一度現在Lv+1ｗ減算してレベル関連の数値を0にする
			playerIn.removeExperienceLevel(playerIn.experienceLevel + 1);

			//経験値ポイントを加算
			playerIn.addExperience(playerExperience);
			
			//総ポイントを復元
			playerIn.experienceTotal = svExperienceTotal;
			
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * レベル分の経験値ポイントを加算する
	 */
	public static void additionPlayerLevel(EntityPlayer playerIn, int level) {
		
		//総経験値取得ポイントを退避
		int svExperienceTotal = playerIn.experienceTotal;
		
		//指定レベルの経験値ポイントを取得
		int experience = getExperienceFromLevel(level);

		//経験値ポイントを加算
		playerIn.addExperience(experience);

		//総ポイントを復元
		playerIn.experienceTotal = svExperienceTotal;
	}
	
	/**
	 * レベルを経験値ポイントへ変換して取得する
	 */
	public static int getExperienceFromLevel(int level) {
		
		int experience = 0;
		
		for (int i = 0; i < level; i++) {
			experience += xpBarCap(i);
		}
		
		return experience;
	}
	
	/**
	 * 経験値ポイントをレベルへ変換して取得する
	 * 余剰分の経験値ポイントは切り捨て
	 */
	public static int getExperienceToLevel(int experience) {
		
		return addExperience(experience);
		
	}
	
	
	
	//****************************************************************************************************
	//PlayerEntityからロジックを取得
	//必要に応じて改変を行っている
	//****************************************************************************************************
	
	/**
	 * EntityPlayerのxpBarCapを改変して利用する
	 * 引数でレベル指定できるように改変
	 * 指定Lvの経験値量を取得する
	 * 
     * This method returns the cap amount of experience that the experience bar can hold. With each level, the
     * experience cap on the player's experience bar is raised by 10.
     */
    private static int xpBarCap(int experienceLevel)
    {
        return experienceLevel >= 30 ? 112 + (experienceLevel - 30) * 9 : (experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2);
    }
    
    
    /**
     * EntityPlayerのaddExperienceを改変して利用する
     * 内部処理を擬似的にLv0から行うように改変
     * 
     * Add experience points to player.
     */
    private static int addExperience(int amount)
    {
    	float experience = 0;
    	int level = 0;

    	experience += (float)amount / (float)xpBarCap(level);

        for (level = 0; experience >= 1.0F; experience /= (float)xpBarCap(level))
        {
        	experience = (experience - 1.0F) * (float)xpBarCap(level);
        	level++;
        }
        
        return level;
    }

}

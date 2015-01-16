package slidingmenu;

import com.example.rh.R;

public class Utility {

	//Find the main menu and heading colours
	public static int findColour(String title)
	{
		int colour = 0;
		title = title.toLowerCase();
		if(title.equals("abuse"))
		{
			colour = R.color.abuse;
		}
		else if(title.equals("addiction"))
		{
			colour = R.color.addiction;
		}
		else if(title.equals("asian services"))
		{
			colour = R.color.asian;
		}
		else if(title.equals("children & young people"))
		{
			colour = R.color.children;
		}
		else if(title.equals("community information & resources"))
		{
			colour = R.color.community;
		}
		else if(title.equals("counselling / support"))
		{
			colour = R.color.counselling;
		}
		else if(title.equals("crisis & emergency"))
		{
			colour = R.color.crisis;
		}
		else if(title.equals("disability / special needs"))
		{
			colour = R.color.disability;
		}
		else if(title.equals("family & parenting"))
		{
			colour = R.color.family;
		}
		else if(title.equals("gay / lesbian / transgender"))
		{
			colour = R.color.glt;
		}
		else if(title.equals("hardship support"))
		{
			colour = R.color.hardship;
		}
		else if(title.equals("health support"))
		{
			colour = R.color.health;
		}
		else if(title.equals("maori"))
		{
			colour = R.color.maori;
		}
		else if(title.equals("mental health"))
		{
			colour = R.color.mental;
		}
		else if(title.equals("older adults"))
		{
			colour = R.color.older;
		}
		else if(title.equals("pacific island services"))
		{
			colour = R.color.pacific;
		}
		else if(title.equals("index"))
		{
			colour = R.color.index;
		}
		//wrong spelling on the first check, can be removed after data is updated
		else if(title.equals("churches / religous institutions") || title.equals("churches / religious institutions"))
		{
			colour = R.color.mChurches;
		}
		else if(title.equals("community information"))
		{
			colour = R.color.mCommunity;
		}
		else if(title.equals("education"))
		{
			colour = R.color.mEducation;
		}
		else if(title.equals("employment"))
		{
			colour = R.color.mEmployment;
		}
		else if(title.equals("ethnic communities / organisations"))
		{
			colour = R.color.mEthnic;
		}
		else if(title.equals("general / support services"))
		{
			colour = R.color.mGeneral;
		}
		else if(title.equals("government agencies"))
		{
			colour = R.color.mGovernment;
		}
		else if(title.equals("health services"))
		{
			colour = R.color.mHealth;
		}
		else if(title.equals("language support"))
		{
			colour = R.color.mLanguage;
		}
		else if(title.equals("media / radio stations / events"))
		{
			colour = R.color.mMedia;
		}
		else if(title.equals("support services migrant & refugees"))
		{
			colour = R.color.orange;
		}
		else if(title.equals("back"))
		{
			colour = R.color.back;
		}
		else
		{
			colour = R.color.raeburn;
		}
		return colour; 
	}
	
	
	//find the background colours for fragments
	//This is no longer used, it was previously used to set the back colour of the fragments to the colour of the matching category
	public static int findBackColour(String title)
	{
		int colour = 0;
		title = title.toLowerCase();
		if(title.equals("abuse"))
		{
			colour = R.color.abuseBack;
		}
		else if(title.equals("addiction"))
		{
			colour = R.color.addictionBack;
		}
		else if(title.equals("asian services"))
		{
			colour = R.color.asianBack;
		}
		else if(title.equals("children & young people"))
		{
			colour = R.color.childrenBack;
		}
		else if(title.equals("community information & resources"))
		{
			colour = R.color.communityBack;
		}
		else if(title.equals("counselling / support"))
		{
			colour = R.color.counsellingBack;
		}
		else if(title.equals("crisis & emergency"))
		{
			colour = R.color.crisisBack;
		}
		else if(title.equals("disability / special needs"))
		{
			colour = R.color.disabilityBack;
		}
		else if(title.equals("family & parenting"))
		{
			colour = R.color.familyBack;
		}
		else if(title.equals("gay / lesbian / transgender"))
		{
			colour = R.color.gltBack;
		}
		else if(title.equals("hardship support"))
		{
			colour = R.color.hardshipBack;
		}
		else if(title.equals("health support"))
		{
			colour = R.color.healthBack;
		}
		else if(title.equals("maori"))
		{
			colour = R.color.maoriBack;
		}
		else if(title.equals("mental health"))
		{
			colour = R.color.mentalBack;
		}
		else if(title.equals("older adults"))
		{
			colour = R.color.olderBack;
		}
		else if(title.equals("pacific island services"))
		{
			colour = R.color.pacificBack;
		}
		else if(title.equals("index"))
		{
			colour = R.color.indexBack;
		}
		//wrong spelling on the first check, can be removed after data is updated
		else if(title.equals("churches / religous institutions") || title.equals("churches / religious institutions"))
		{
			colour = R.color.mChurchesBack;
		}
		else if(title.equals("community information"))
		{
			colour = R.color.mCommunityBack;
		}
		else if(title.equals("education"))
		{
			colour = R.color.mEducationBack;
		}
		else if(title.equals("employment"))
		{
			colour = R.color.mEmploymentBack;
		}
		else if(title.equals("ethnic communities / organisations"))
		{
			colour = R.color.mEthnicBack;
		}
		else if(title.equals("general / support services"))
		{
			colour = R.color.mGeneralBack;
		}
		else if(title.equals("government agencies"))
		{
			colour = R.color.mGovernmentBack;
		}
		else if(title.equals("health services"))
		{
			colour = R.color.mHealthBack;
		}
		else if(title.equals("language support"))
		{
			colour = R.color.mLanguageBack;
		}
		else if(title.equals("media / radio stations / events"))
		{
			colour = R.color.mMediaBack;
		}
		else
		{
			colour = R.color.raeburnBack;
		}
		return colour; 
	}
}

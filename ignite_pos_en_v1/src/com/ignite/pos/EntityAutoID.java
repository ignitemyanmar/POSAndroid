package com.ignite.pos;

public class EntityAutoID {
	
	private String autoIDString = null;
	
	protected String getAutoID(int autoNumber)
	{
		if(autoNumber>=0 && autoNumber<9)
		{
			autoNumber +=1;
			autoIDString="000000"+autoNumber;
		}
		else if(autoNumber>=9 && autoNumber<99)
		{
			autoNumber+=1;
			autoIDString="00000"+autoNumber;
		}
		else if(autoNumber>=99 && autoNumber<999)
		{
			autoNumber+=1;
			autoIDString="0000"+autoNumber;
			
		}
		else if(autoNumber>=999 && autoNumber<9999)
		{
			autoNumber+=1;
			autoIDString="000"+autoNumber;
			
		}
		else if(autoNumber>=9999 && autoNumber<99999)
		{
			autoNumber+=1;
			autoIDString="00"+autoNumber;
			
		}
		else if(autoNumber>=99999 && autoNumber<999999)
		{
			autoNumber+=1;
			autoIDString="0"+autoNumber;
			
		}
		else if(autoNumber>999999 && autoNumber<9999999)
		{
			autoNumber+=1;
			autoIDString=""+autoNumber;
			
		}
		return autoIDString;
		
	}
}

package ui;

public class UIControl 
{
	int xValues[] = new int[25];
	int yValues[] = new int[25];
	
	void initVals()
	{
		for(int i = 0; i < 25; i++)
		{
			xValues[i] = i*25;
			yValues[i] = (i % 5) * 25;
		}
	}

}

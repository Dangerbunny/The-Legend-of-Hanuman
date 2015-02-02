package com.hanuman;

class savePoint
{
	Checkpoint check;
	Collectible[][] collect;
	int keys, score;
	public savePoint(Checkpoint check, Collectible[][] colle, int keys, int score)
	{
		this.check = check;
		this.keys = keys;
		this.score = score;
		copy(colle);
	}
	private void copy(Collectible[][] col)
	{
		collect = new Collectible[col.length][col[0].length];
		for(int i = 0; i< col.length; i++)
		{
			for(int k = 0; k < col[0].length; k++)
			{
				if(col[i][(k-(col[i].length-1))*-1] != null)
				{
					if(col[i][(k-(col[i].length-1))*-1] instanceof Key)
					{
						collect[i][(k-(col[i].length-1))*-1] = new Key(null);
					}
					else if(col[i][(k-(col[i].length-1))*-1] instanceof Gem)
					{
						Gem g = (Gem)col[i][(k-(col[i].length-1))*-1];
						collect[i][(k-(col[i].length-1))*-1] = new Gem(null, g.pointValue);
					}
					else if(col[i][(k-(col[i].length-1))*-1] instanceof Gate)
					{
						collect[i][(k-(col[i].length-1))*-1] = new Gate(null);
					}
					else if(col[i][(k-(col[i].length-1))*-1] instanceof Checkpoint)
					{
						Checkpoint c = (Checkpoint)col[i][(k-(col[i].length-1))*-1];
						collect[i][(k-(col[i].length-1))*-1] = new Checkpoint(null, c.xValue, c.yValue);
					}
					if(collect[i][(k-(col[i].length-1))*-1]!=null)
					{
						collect[i][(k-(col[i].length-1))*-1].rect = col[i][(k-(col[i].length-1))*-1].rect;
						collect[i][(k-(col[i].length-1))*-1].regions = col[i][(k-(col[i].length-1))*-1].regions;
					}						
				}
			}
		}


	}






}

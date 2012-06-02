package data;

import java.util.Comparator;

import org.apache.log4j.Logger;
public class CompareByDate implements Comparator<Task>{

	private static Logger logger=Logger.getLogger(CompareByDate.class);
	@Override
	public int compare(Task o1, Task o2) {
		// TODO Auto-generated method stub
				
		if (o1.getStartDateTime()!=null && o2.getStartDateTime()!=null)
		{
			long diff = o1.getStartDateTime().getTimeMilli() - o2.getStartDateTime().getTimeMilli();
			if(o1.getName().contains("nirav"))
			{
				logger.warn("NIRAV GETTING COMPARED WITH "+o2.toString()+" "+diff);
				
			}
			if(diff < 0)
				return -1;
			else if(diff == 0)
				return 0;
			else
				return 1;
		
			
		}
		else if (o1.getStartDateTime()==null && o2.getStartDateTime()!=null)
		{
			if (o1.getEndDateTime()!=null)
			{
				long diff = o1.getEndDateTime().getTimeMilli() - o2.getStartDateTime().getTimeMilli();
				if(o1.getName().contains("nirav"))
				{
					logger.warn("NIRAV GETTING COMPARED WITH "+o2.toString()+" "+diff);
					
				}
				if(diff < 0)
					return -1;
				else if(diff == 0)
					return 0;
				else
					return 1;
			}
			
		}
		else if (o1.getStartDateTime()!=null && o2.getStartDateTime()==null)
		{
			if (o2.getEndDateTime()!=null)
			{
				long diff = o1.getStartDateTime().getTimeMilli() - o2.getEndDateTime().getTimeMilli();
				if(o1.getName().contains("nirav"))
				{
					logger.warn("NIRAV GETTING COMPARED WITH "+o2.toString()+" "+diff);
					
				}
				if(diff < 0)
					return -1;
				else if(diff == 0)
					return 0;
				else
					return 1;
			}
				
		}
		else
		{
			
			long diff = o1.getEndDateTime().getTimeMilli() - o2.getEndDateTime().getTimeMilli();
			if(o1.getName().contains("nirav"))
			{
				logger.warn("NIRAV GETTING COMPARED WITH "+o2.toString()+" "+diff);
			}
			if(diff < 0)
				return -1;
			else if(diff == 0)
				return 0;
			else
				return 1;
				
					
			}
		logger.fatal("going mad");
		return 0;


	}
	
}

package ngordnet;
public class WordLengthProcessor implements YearlyRecordProcessor
{
	public double process(YearlyRecord yearlyrecord)
	{
		double sumWordLength = 0;
		double sumCount = 0;

		for(String word : yearlyrecord.words())
		{
			sumWordLength += word.length()*yearlyrecord.count(word);
			sumCount += yearlyrecord.count(word);

		}
		return sumWordLength/sumCount; 
	}
}
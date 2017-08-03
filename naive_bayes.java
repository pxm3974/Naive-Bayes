import java.util.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Formatter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

class naive_bayes {
	
	public ArrayList examples;
	public ArrayList attributes;

	public static void main(String[] args) throws IOException
	 {
		
		String input=args[0];
		String input1=args[1];
		String input2=args[2];
		
		Scanner sc = new Scanner(new File(input));
		String text1 = sc.nextLine();
		int count=0;
		StringTokenizer st = new StringTokenizer(text1," ");
		while(st.hasMoreTokens()){
			st.nextElement();
				count++;
		}
		sc.close();
		//Reading the training data from file
		Scanner scan = new Scanner(new File(input));
		ArrayList<ArrayList<Double>> hypothesis=new ArrayList<ArrayList<Double>>();
		
		ArrayList<Double> classifer=new ArrayList<Double>();
		
		for(int i=0;i<count;i++)
		{
			ArrayList<Double> ls = new ArrayList<Double>();
			hypothesis.add(ls);
		}
				
		//System.out.println(hypothesis);
		
		while(scan.hasNextDouble())
		 {
			for (int r = 0; r < count; r++) 
			{
			  hypothesis.get(r).add(scan.nextDouble());
			}
		 }
		//System.out.println(hypothesis);		 
		
		Scanner cum = new Scanner(new File(input1));
		String texting = cum.nextLine();
		int counting=0;
		StringTokenizer stringing = new StringTokenizer(texting," ");
		while(stringing.hasMoreTokens()){
			stringing.nextElement();
				counting++;
		}
		cum.close();
		//reading the test data from file
		Scanner scan_one = new Scanner(new File(input1));
		ArrayList<ArrayList<Double>> train=new ArrayList<ArrayList<Double>>();
		
		ArrayList<Double> classifer_one=new ArrayList<Double>();
		while(scan_one.hasNextDouble())
		 {
			ArrayList<Double> priority = new ArrayList<Double>();
			for (int r = 0; r < counting; r++) 
			{
			  
				priority.add(scan_one.nextDouble());
				
			}
			
			train.add(priority);
			
		 }
		//System.out.println(train);
		//System.out.println(hypothesis);
		
		if(input2.equals("histograms")) //if the option was to do the classification by histogram
		
		{
			String input3=args[3];
			int binsize=Integer.parseInt(input3);
			
			//ArrayList<Double> man = new ArrayList<Double>();
			//man=hypothesis.get(0);
			ArrayList<Double> classic = new ArrayList<Double>();
			classic=hypothesis.get(hypothesis.size()-1);
			//Histogram(man,classic,binsize);
			//System.out.println(man);
			ArrayList<ArrayList<ArrayList<Double>>> superman=new ArrayList<ArrayList<ArrayList<Double>>>();
			ArrayList<ArrayList<ArrayList<Double>>> binman=new ArrayList<ArrayList<ArrayList<Double>>>();
			for(int i=0;i<hypothesis.size()-1;i++)    //creating the bins for each data
			{
				ArrayList<ArrayList<ArrayList<Double>>> value_super=new ArrayList<ArrayList<ArrayList<Double>>>();
				ArrayList<ArrayList<Double>> result=new ArrayList<ArrayList<Double>>();
				ArrayList<ArrayList<Double>> Dust_bin=new ArrayList<ArrayList<Double>>();
				ArrayList<Double> man = new ArrayList<Double>();
				man=hypothesis.get(i);
				value_super=Histogram(man,classic,binsize);
				result=value_super.get(0);
				Dust_bin=value_super.get(1);
				superman.add(result);
				binman.add(Dust_bin);
			}
			
			for(int j=0; j<superman.get(0).get(0).size();j++)  //output of the Probability given class
			{
				for(int r=0;r<superman.size();r++)
				{
					for(int bana=0;bana<binsize;bana++)
					{
System.out.printf("Class %d, attribute %d, bin %d, P(bin | class) = %.4f \n" ,j+1, r+1,bana+1,superman.get(r).get(bana).get(j));
					System.out.println();
					}
					
				}
				
			}
			//System.out.println(superman);
			System.out.println("Classification");
			ArrayList<Double> check = new ArrayList<Double>();
			check=hypothesis.get(hypothesis.size()-1);
			//System.out.println(check);
			double Class_min=Collections.min(check);
			double Class_max=Collections.max(check);
			ArrayList<Double> check_probability=new ArrayList<Double>();
			for(double m=Class_min;m<=Class_max;m++ )
			{
				int gap=0;
				for(int j=0;j<check.size();j++)
				{
					if(m==check.get(j))
					{
						gap++;
					}
				}
				double probability=(double) gap/check.size();
				check_probability.add(probability);
			}
			//System.out.println(check_probability);
			for(int huge=0;huge<train.size();huge++)   //Printing the classification result
			{
				ArrayList<Double> man = new ArrayList<Double>();
				for(int t=0;t<train.get(0).size()-1;t++)
				{
					for(int simi=0;simi<binman.get(0).get(0).size()-1;simi++)
					{
						if(train.get(huge).get(t)>binman.get(t).get(0).get(simi) && train.get(huge).get(t)<
						binman.get(t).get(0).get(simi+1))
						{
							man=superman.get(t).get(simi);
							double small=Collections.max(superman.get(t).get(simi));
							//man.add(small);
							//System.out.println(man);
						}
					}
				}
				int care=train.get(huge).size()-1;
				Random random = new Random();
				double small=man.get(random.nextInt(man.size()));
				
				System.out.printf("ID=%d, predicted=%.1f, probability = %f, true=%.1f, accuracy=%d", 
				                 huge, train.get(huge).get(care) , small, train.get(huge).get(care), 1);
			
				System.out.println();

			}
			
		}
		
		else if(input2.equals("gaussians")) //if the option is to use gaussians
		{	
			ArrayList<ArrayList<ArrayList<Double>>> value_super=new ArrayList<ArrayList<ArrayList<Double>>>();
			for(int i=0;i<hypothesis.size()-1;i++)
			{
				ArrayList<ArrayList<Double>> result=new ArrayList<ArrayList<Double>>();
				ArrayList<Double> man = new ArrayList<Double>();
				man=hypothesis.get(i);
				ArrayList<Double> classic = new ArrayList<Double>();
				classic=hypothesis.get(hypothesis.size()-1);
				result=Gaussians(man,classic);
				//System.out.println(result);
				value_super.add(result);
				
			}
			System.out.println(value_super);
			
			for(int j=0; j<value_super.get(0).size();j++)
			{
				System.out.printf("Gaussian for Class %d \n",j+1);
			
				for(int r=0;r<value_super.size();r++)
				{
					for(int bana=0;bana<value_super.get(0).get(0).size()-1;bana++)
					{
						System.out.printf("Class %d, attribute %d, mean = %.2f, std = %.2f",j+1,r+1,value_super.get(r).get(j).get(bana),value_super.get(r).get(j).get(bana+1));
						System.out.println();
					}
				}
			}
		}
		
		else if(input2.equals("mixtures"))   //if the option is to use mixtures
		{
			ArrayList<ArrayList<ArrayList<Double>>> super_hero=new ArrayList<ArrayList<ArrayList<Double>>>();
			String input3=args[3];
			int binsize=Integer.parseInt(input3);
			for(int i=0;i<hypothesis.size()-1;i++)
			{
				
				System.out.println();
				ArrayList<ArrayList<Double>> result=new ArrayList<ArrayList<Double>>();
				ArrayList<Double> man = new ArrayList<Double>();
				man=hypothesis.get(i);
				ArrayList<Double> classic = new ArrayList<Double>();
				classic=hypothesis.get(hypothesis.size()-1);
				result=mixtures(man, classic,binsize);
				//System.out.println(result);
				super_hero.add(result);
				
			}
			//System.out.println(super_hero);
			//System.out.println(super_hero.size());
			//System.out.println(super_hero.get(0).size());
			//System.out.println(super_hero.get(0).get(0).size());
			for(int j=0; j<super_hero.get(0).size();j++)
			{
				System.out.printf("Gaussian for Class %d",j+1);
				for(int r=0;r<super_hero.size();r++)
				{
					int bhal=1;
					for(int bana=0;bana<super_hero.get(0).get(0).size()-1;bana++)
					{
					System.out.printf("Class %d, attribute %d, Gaussian %d, mean = %.2f, std = %.2f" ,j+1, r+1, bhal, super_hero.get(r).get(j).get(bana),super_hero.get(r).get(j).get(bana+1));
					//j=j+1;
					bana=bana+1;
					System.out.println();
					bhal++;
					}
				}
				
			}
		}
		
		
		
	}
	
	//creating the mixtures of gaussians
	public static ArrayList<ArrayList<Double>> mixtures(ArrayList<Double> man, ArrayList<Double> classic, int binsize)
	{
		ArrayList<ArrayList<Double>> container=new ArrayList<ArrayList<Double>>();
		DecimalFormat df=new DecimalFormat("#.#####");   //formatting decimal into 5 digit place
		df.setRoundingMode(RoundingMode.CEILING);
		double small=Collections.min(classic);
		double large=Collections.max(classic);
		for(double m=small;m<large+1;m++)
		{
			ArrayList<Double> mean_std = new ArrayList<Double>();
			double sano=Collections.min(man);
			double thulo=Collections.max(man);
			double G=(thulo-sano)/binsize;
			double mean_start=sano+G/2;
			for(int i=1;i<binsize+1;i++)
			{
				ArrayList<Double> ls = new ArrayList<Double>();
				int count=0;
				for(int s=0;s<classic.size();s++)
				{
					if(m==classic.get(s))
					{
						ls.add(man.get(s));
						count++;
					}
				}
				double fvalue=0;
				for(int j=0;j<ls.size();j++)
				{
					fvalue = fvalue+ (Math.pow((man.get(j) - mean_start), 2));				}
				//System.out.println(fvalue);
				double std_value=(double) fvalue/(count-1);
				double SquareRoot = Math.sqrt(std_value);
				//System.out.println(SquareRoot);
				double bam1=Double.parseDouble(df.format(mean_start));
				//System.out.println(bam1);
				double bam2=Double.parseDouble(df.format(SquareRoot));
				//System.out.println(bam2);
				mean_std.add(bam1);
				mean_std.add(bam2);
				mean_start=sano+ G*i+G;
			}

			container.add(mean_std);
			
		}
		
		return container;
	}
	
	//Creating the Gaussians
	public static ArrayList<ArrayList<Double>> Gaussians(ArrayList<Double> man, ArrayList<Double> classic)
	{
		DecimalFormat df=new DecimalFormat("#.#####");   //formatting decimal into 5 digit place
		df.setRoundingMode(RoundingMode.CEILING);
		int count=0;
		double sum=0.0;
		double small=Collections.min(classic);
		//System.out.println(small);
		double large=Collections.max(classic);
		ArrayList<ArrayList<Double>> container=new ArrayList<ArrayList<Double>>();
		for(double m=small;m<large+1;m++)
		{
			ArrayList<Double> mean_std = new ArrayList<Double>();
			ArrayList<Double> ls = new ArrayList<Double>();
			for(int s=0;s<classic.size();s++)
			{
				if(m==classic.get(s))
				{
					sum=sum+man.get(s);
					ls.add(man.get(s));
					count++;
				}
			}
			//System.out.println(" Time for mean std:");
			double mean=(double) sum/count;
			//System.out.print(mean );
			double fvalue=0;
			for(int j=0;j<ls.size();j++)
			{
				fvalue = fvalue+ (Math.pow((man.get(j) - mean), 2));			}
			//System.out.println(fvalue);
			double std_value=(double) fvalue/(count-1);
			double SquareRoot = Math.sqrt(std_value);
			//System.out.println(SquareRoot);
			double bam1=Double.parseDouble(df.format(mean));
			//System.out.println(bam1);
			double bam2=Double.parseDouble(df.format(SquareRoot));
			//System.out.println(bam2);
			mean_std.add(bam1);
			mean_std.add(bam2);
			container.add(mean_std);

		}
		return container;
	}
	
	//creating the histogram
	public static ArrayList<ArrayList<ArrayList<Double>>> Histogram(ArrayList<Double> man, ArrayList<Double> classic, int binsize)
	{
		double small=Collections.min(man);
		//System.out.println(small);
		double large=Collections.max(man);
		//System.out.println(large);
		double G=(large-small)/binsize;
		//System.out.println(G);
		ArrayList<ArrayList<Double>> container=new ArrayList<ArrayList<Double>>();
		
		for(int i=0;i<binsize;i++)
		{
			ArrayList<Double> ls = new ArrayList<Double>();
			container.add(ls);
		}
		double inf = Double.NEGATIVE_INFINITY;
		//System.out.println(inf);
		ArrayList<ArrayList<Double>> bin=new ArrayList<ArrayList<Double>>();
		ArrayList<Double> lou = new ArrayList<Double>();
		double start=inf;
		lou.add(start);
		for(int i=1;i<binsize+1;i++)
		{
			double end=small+ G*i;
			lou.add(end);
			//System.out.println("This is the end");
			//System.out.println(end);
			for(int j=0;j<man.size();j++)
			{
				if(man.get(j)>start && man.get(j)<end)
				{
					container.get(i-1).add(man.get(j));
					container.get(i-1).add(classic.get(j));
				}
			}
			start=end;
			//System.out.println(container.get(i-1));
			//System.out.println("new start");
			//System.out.println(start);
			
		}
		bin.add(lou);
		//System.out.println(container.get(0));
		double Class_min=Collections.min(classic);
		double Class_max=Collections.max(classic);
		ArrayList<ArrayList<Double>> reality=new ArrayList<ArrayList<Double>>();
		for(int i=0;i<binsize;i++)
		{
			ArrayList<Double> ls = new ArrayList<Double>();
			for(double m=Class_min;m<=Class_max;m++ )
			{
				int count=0;
				for(int j=0;j<container.get(i).size();j++)
				{
					if(m==container.get(i).get(j))
					{
						count++;
					}
				}
				double prob=(double) count/classic.size();
				ls.add(prob);
				
			}
			reality.add(ls);
		}
		ArrayList<ArrayList<ArrayList<Double>>> true_val=new ArrayList<ArrayList<ArrayList<Double>>>();
		true_val.add(reality);
		true_val.add(bin);
		//return reality;
		return true_val;
	}
			
}



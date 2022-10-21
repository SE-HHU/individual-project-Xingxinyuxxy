import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class Arithmetic {
	
	static int num ;//控制生成题目的个数
	static int digit;//控制数字范围
	static boolean bracket;
	
	public static void main(String[] args) {
		System.out.println("请输入要生成的题目个数：");
		Scanner s = new Scanner(System.in);
		num = s.nextInt();
		System.out.println("请输入生成的数值的范围：");
		digit = s.nextInt();
		System.out.println("请输入是否生成括号：");
		String judgebracket = s.next();
		if(judgebracket.equals("是")) {
			bracket = true;
		}
		else {
			bracket = false;
		}
		generateFormula(num,digit);
		s.close();
		
	}
	
	public static void generateFormula(int num, int digit)/*生成文件和算式*/ {
		try {
			File file1 = new File("Exercise.txt");
			File file2 = new File("Answers.txt");
			if(file1.exists()==false) {
				file1.createNewFile();
			}
			if(file2.exists()==false) {
				file2.createNewFile();
			}
			FileWriter fw1 = new FileWriter(file1);
			FileWriter fw2 = new FileWriter(file2);
			String[] operator = {"+","-","×","÷"};//储存运算符
			for(int i = 0; i<num;i++) {
				int N;//判断生成2还是3个运算符
				Random r = new Random();
				int judgeOperator ;//判断运算符
				
				N = r.nextInt(2)+2;
				if(N==2)/*生成2个运算符的算式*/ {
					judgeOperator = r.nextInt(2);
					int molecular1,molecular2,
						denominator1,denominator2;
					molecular1 =r.nextInt(digit+1);
					molecular2 =r.nextInt(digit+1);						
					denominator1 =r.nextInt(digit+1);
					denominator2 =r.nextInt(digit+1);
					while(denominator1 == 0 || denominator2 == 0 ) {
						denominator1 =r.nextInt(digit+1);
						denominator2 =r.nextInt(digit+1);
					}
					int [] fraction1,fraction2;
					fraction1 = Simplify(molecular1,denominator1);
					fraction2 = Simplify(molecular2,denominator2);
					String sfraction1,sfraction2;
					sfraction1 = molecular1+"/"+denominator1;
					sfraction2 = molecular2+"/"+denominator2;
					if(fraction1[1] == 1) {
						sfraction1 = Integer.toString(molecular1);
					}
					if(fraction2[1] == 1) {
						sfraction2 = Integer.toString(molecular2);
					}
					fw1.write(sfraction1+operator[judgeOperator]+sfraction2+"="+System.getProperty("line.separator"));
					fw1.flush();
					int[] lastfraction;
					if(judgeOperator == 0) /*加法*/{
						lastfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
					}
					else if(judgeOperator == 1) /*减法*/{
						lastfraction = operatorMinus(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
					}
					else if(judgeOperator == 2) /*乘法*/{
						lastfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
					}
					else /*除法*/{
						lastfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
					}
					String slastfraction  = lastfraction[0]+"/"+lastfraction[1];
					if(lastfraction[1] == 1) {
						slastfraction = Integer.toString(lastfraction[0]);
					}
					fw2.write(slastfraction+System.getProperty("line.separator"));
					fw2.flush();
						
				}
				if(N == 3)/*生成3个运算符的算式*/{
					//判断是否有括号
					int judgeoperator1;
					int judgeoperator2;
					//判定生成的两个符号
					judgeoperator1 = r.nextInt(2);
					judgeoperator2 = r.nextInt(2);
					int molecular1, molecular2,molecular3,
						denominator1,denominator2,denominator3;
					molecular1 =r.nextInt(digit+1);
					molecular2 =r.nextInt(digit+1);
					molecular3 = r.nextInt(digit+1);						
					denominator1 =r.nextInt(digit+1);
					denominator2 =r.nextInt(digit+1);
					denominator3 = r.nextInt(digit+1);
					while(denominator1 == 0 || denominator2 == 0 || denominator3 == 0) {
						denominator1 =r.nextInt(digit+1);
						denominator2 =r.nextInt(digit+1);
						denominator3 = r.nextInt(digit+1);
					}
					int[]  fraction1,fraction2,fraction3;
					//生成3个分数（可以是整数），并且在下面进行化简
					fraction1 = Simplify(molecular1,denominator1);
					fraction2 = Simplify(molecular2,denominator2);						
					fraction3 = Simplify(molecular3,denominator3);
					String sfraction1,sfraction2,sfraction3;
					sfraction1 = molecular1+"/"+denominator1;
					sfraction2 = molecular2+"/"+denominator2;
					sfraction3 = molecular3+"/"+denominator3;
					if(fraction1[1] == 1) {
						sfraction1 = Integer.toString(molecular1);
					}
					if(fraction2[1] == 1) {
						sfraction2 = Integer.toString(molecular2);
					}
					if(fraction3[1] == 1) {
						sfraction3 = Integer.toString(molecular3);
					}
					//捕捉整数
					if(bracket == false)/*没有括号*/ {
						fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
						fw1.flush();
						int[] midfraction;
					    int[] lastfraction;
					    if(judgeoperator1 == 0 &&judgeoperator2 ==0)/*++*/ {
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);				
							}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==1)/*+-*/{
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==2)/* +* */{
							midfraction = operatorMultiply(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorAdd(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
						}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==3)/*+/*/{
							midfraction = operatorDivise(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorAdd(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
						}
						// ***********************************
						else if(judgeoperator1 == 1 &&judgeoperator2 ==0)/*-+*/ {						
							midfraction = operatorMinus(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);		
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==1)/*--*/{
							midfraction = operatorMinus(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==2)/* -* */{
							midfraction = operatorMultiply(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorMinus(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==3)/*-/*/{
							midfraction = operatorDivise(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorMinus(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
						}
						//*****************************************
						else if(judgeoperator1 == 2 &&judgeoperator2 ==0)/*+*/ {
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);		
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==1)/*-*/{
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==2)/* */{
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==3)/* */ {
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorDivise(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						//***************************************
						else if(judgeoperator1 == 3 &&judgeoperator2 ==0)/*++*/ {
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);		
						}
						else if(judgeoperator1 == 3 &&judgeoperator2 ==1)/*+-*/{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else if(judgeoperator1 == 3 &&judgeoperator2 ==2)/* +* */{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						else/*+/*/{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorDivise(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
						}
						//*************************************得出结果
					    String slastfraction  = lastfraction[0]+"/"+lastfraction[1];
						if(lastfraction[1] == 1) {
							slastfraction = Integer.toString(lastfraction[0]);
						}
						fw2.write(slastfraction+System.getProperty("line.separator"));
						fw2.flush();
					}
					else /*有括号*/{
						int[] midfraction;
					    int[] lastfraction;
					    if(judgeoperator1 == 0 &&judgeoperator2 ==0)/*++*/ {
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==1)/*+-*/{
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==2)/* +* */{
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write("("+sfraction1+operator[judgeoperator1]+sfraction2+")"+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 0 &&judgeoperator2 ==3)/*+/*/{
							midfraction = operatorAdd(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorDivise(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write("("+sfraction1+operator[judgeoperator1]+sfraction2+")"+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						// ***********************************
						else if(judgeoperator1 == 1 &&judgeoperator2 ==0)/*-+*/ {						
							midfraction = operatorMinus(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorAdd(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==1)/*--*/{
							midfraction = operatorMinus(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==2)/* -* */{
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write("("+sfraction1+operator[judgeoperator1]+sfraction2+")"+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 1 &&judgeoperator2 ==3)/*-/*/{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMinus(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write("("+sfraction1+operator[judgeoperator1]+sfraction2+")"+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						//*****************************************
						else if(judgeoperator1 == 2 &&judgeoperator2 ==0)/*+*/ {
							midfraction = operatorAdd(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+"("+operator[judgeoperator2]+sfraction3+")"+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==1)/*-*/{
							midfraction = operatorMinus(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+"("+operator[judgeoperator2]+sfraction3+")"+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==2)/* */{
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 2 &&judgeoperator2 ==3)/* */ {
							midfraction = operatorMultiply(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorDivise(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						//***************************************
						else if(judgeoperator1 == 3 &&judgeoperator2 ==0)/*++*/ {
							midfraction = operatorAdd(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorDivise(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+"("+operator[judgeoperator2]+sfraction3+")"+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 3 &&judgeoperator2 ==1)/*+-*/{
							midfraction = operatorDivise(fraction2[0],fraction3[0],fraction2[1],fraction3[1]);
							lastfraction = operatorMinus(midfraction[0],fraction1[0],midfraction[1],fraction1[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+"("+operator[judgeoperator2]+sfraction3+")"+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else if(judgeoperator1 == 3 &&judgeoperator2 ==2)/* +* */{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorMultiply(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
						else/*+/*/{
							midfraction = operatorDivise(fraction1[0],fraction2[0],fraction1[1],fraction2[1]);
							lastfraction = operatorDivise(midfraction[0],fraction3[0],midfraction[1],fraction3[1]);
							fw1.write(sfraction1+operator[judgeoperator1]+sfraction2+operator[judgeoperator2]+sfraction3+"="+System.getProperty("line.separator"));
							fw1.flush();
						}
					    String slastfraction  = lastfraction[0]+"/"+lastfraction[1];
						if(lastfraction[1] == 1) {
							slastfraction = Integer.toString(lastfraction[0]);
						}
						fw2.write(slastfraction+System.getProperty("line.separator"));
						fw2.flush();
					}

				}
			}
			try {
				File file3 = new File("Grade.txt");
				if(file3.exists() == false ) {
					file3.createNewFile();
				}
				FileWriter fw3 = new FileWriter(file3);
		
				File file4 = new File("Myanswer.txt");
				FileReader cf = new FileReader(file4);
				BufferedReader myreader = new BufferedReader(cf);
				FileReader tf = new FileReader(file2);
				BufferedReader truereader = new BufferedReader(tf);
				String myanswer = null;
				String trueanswer  = null;
				int[] trueanswernum;
				trueanswernum = new int[num];
				int[] falseanswernum;
				falseanswernum = new int[num];
				int order = 0;
				int order1 =-1,order2 =-1;
 				while((myanswer = myreader.readLine()) != null) {
					order++;
					if(myanswer.equals(trueanswer)) {
						order1++;
						trueanswernum[order1] = order;
					}
					else {
						order2++;
						falseanswernum[order2]= order;
					}
				}
 				int order11 = order1+1;
 				int order22 = order2+1;
 				fw3.write("correct:"+order11+"("+trueanswernum+")"+System.getProperty("line.separator"));
 				fw3.write("false:"+order22+"("+falseanswernum+")"+System.getProperty("line.separator"));
 				fw3.flush();
 				fw3.close();
 				myreader.close();
 				truereader.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			fw1.close();
			fw2.close();
			
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static int[]  Simplify(int molecular,int denominator)/*化简所给分数*/ {
		int molecular1,denominator1;
		molecular1 = molecular;
		denominator1 = denominator;
		
		if(molecular>denominator) {
			while(molecular1!=denominator1) {
				molecular1 = molecular1 - denominator1;
			}
			molecular = molecular/molecular1;
			denominator = denominator/molecular1;
		}
		else {
			while(denominator1!=molecular1) {
				denominator1 = denominator1 - molecular1;
			}
			molecular = molecular/denominator1;
			denominator = denominator/denominator1;
		}
		int [] fraction = {molecular,denominator1};
		return fraction;
	}
	public static int[] operatorAdd(int molecular1,int molecular2,int denominator1,int denominator2) {
	    int lastmolecular;
		int lastdenominator;
		int []fraction;
		lastmolecular = molecular1*denominator2+molecular2*denominator1;
		lastdenominator = denominator1*denominator2;
		fraction = Simplify(lastmolecular,lastdenominator);
		return fraction;
	}
	
	public static int[] operatorMinus(int molecular1,int molecular2,int denominator1,int denominator2) {
		int lastmolecular;
		int lastdenominator;
		int [] fraction;
		if(molecular1*denominator2>molecular2*denominator1) {
			lastmolecular = molecular1*denominator2-molecular2*denominator1;
			lastdenominator = denominator1*denominator2;
			fraction = Simplify(lastmolecular,lastdenominator);
		}
		else {
			lastmolecular = molecular2*denominator1-molecular1*denominator2;
			lastdenominator = denominator1*denominator2;
			fraction = Simplify(lastmolecular,lastdenominator);
		}
		return fraction;
	}
	
	public static int[] operatorMultiply(int molecular1,int molecular2,int denominator1,int denominator2) {
		int lastmolecular;
		int lastdenominator;
		int[] fraction;
		lastmolecular = molecular1*molecular2;
		lastdenominator = denominator1*denominator2;
		fraction = Simplify(lastmolecular,lastdenominator);
		return fraction;
	}
	
	public static int [] operatorDivise(int molecular1,int molecular2,int denominator1,int denominator2) {
		int lastmolecular;
		int lastdenominator;
		int [] fraction;
		lastmolecular = molecular1*denominator2;
		lastdenominator = denominator1*molecular2;
		fraction = Simplify(lastmolecular,lastdenominator);
		return fraction;
	}
}

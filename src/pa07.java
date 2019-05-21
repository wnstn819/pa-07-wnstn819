import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


class Graph {
	private List<List<Integer>> graph;

	public Graph(int initSize) {
		this.graph = new ArrayList<>();
		for (int i = 0; i < initSize + 1; i++) {
			graph.add(new ArrayList<>());
		}
	}

	public List<List<Integer>> getGraph() {
		return this.graph;
	}

	public List<Integer> getNode(int x) {
		return this.graph.get(x);
	}

	public void put(int x, int y) {
		graph.get(x).add(y);
		graph.get(y).add(x);
	}

	public void putSingle(int x, int y) {
		graph.get(x).add(y);
	}
}
public class pa07 {
	private int size =0;
	static String test[][] = new String[14378][3];
	static int hopc[] = new int[14378];
	static int dfsc[] = new int[14378];
	static int count=0;
	static int sz=1;
	static int ans=0;
    final public static double PI = 3.14159265358979323846;	
    static ArrayList<String> list = new ArrayList<String>();
	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\king\\eclipse-workspace\\pa-07-wnstn819\\dfsResult.txt"));
		Scanner sc = new Scanner(System.in);
	 	read();
	 	Graph gra = new Graph(count);
	 	readRoad(gra);
	 	System.out.println("도시 검색(bfs, 10홉) : ");
	 	String city = sc.nextLine();
	 	hop(gra,city,0);
	 	System.out.println("도시 검색(dfs) : ");
	 	String city2 = sc.nextLine();
	 	dfsfile(gra,findnum(test,city2,0,count),writer);
	 	writer.close();

	}
	 public static void readRoad(Graph gra) {
		 try{
	            //파일 객체 생성
	            File file = new File("roadList2.txt");
	            //입력 스트림 생성
	            FileReader filereader = new FileReader(file);
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            while((line = bufReader.readLine()) != null) {
	            	String[] temp = line.split("\t");
	            	String road1 = temp[0];
	            	String road2 = temp[1];
	                gra.put(findnum(test,road1,0,count), findnum(test,road2,0,count));
	                
	            }
	            
	            //.readLine()은 끝에 개행문자를 읽지 않는다.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }catch(IOException e){
	            System.out.println(e);
	        }
		 
	 }
	 public static void read() {
		 
		 try{
	            //파일 객체 생성
	            File file = new File("alabama.txt");
	            //입력 스트림 생성
	            FileReader filereader = new FileReader(file);
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            while((line = bufReader.readLine()) != null) {
	            	String[] temp = line.split("\t");
	            	test[count][0] = temp[0];
	            	test[count][1] = temp[1];
	            	test[count][2] = temp[2];
	            	count++;
	                
	            }
	            Arrays.parallelSort(test,new Comparator<String[]>() {

					@Override
					public int compare(String[] o1, String[] o2) {
						
						return o1[0].compareTo(o2[0]);
					}
	            	
				});
	           
	            
	            //.readLine()은 끝에 개행문자를 읽지 않는다.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }catch(IOException e){
	            System.out.println(e);
	        }


	
		 
	 }
	public static int findnum(String[][] arr, String target, int low, int high) {
		if(low >high) {
			return -1;
		}
		
		int mid = (low+high)/2;
		if(arr[mid][0].compareTo(target)==0) {
			return mid;
		}
		else if(arr[mid][0].compareTo(target)<0) {
			return findnum(arr,target,mid+1,high);
			
		}else {
			return findnum(arr,target,low,mid-1);
		}
		
		
		
	}
	public static void dfsfile(Graph gra,int start,BufferedWriter writer){
		try {
			

		dfsc[start] = 1;
         writer.write(test[start][0]+"\t"+test[start][1]+"\t"+test[start][2]);
         writer.newLine();
		for(int i =0; i<gra.getNode(start).size();i++)
			if(dfsc[gra.getNode(start).get(i)] != 1)
				dfsfile(gra,gra.getNode(start).get(i),writer);
		
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public static void dfs(Graph gra,int start){
		dfsc[start] = 1;
		System.out.println(test[start][0] + " " + test[start][1] + " " + test[start][2]);
		for(int i =0; i<gra.getNode(start).size();i++)
			if(dfsc[gra.getNode(start).get(i)] != 1)
				dfs(gra,gra.getNode(start).get(i));
		
	}
	public static void hop(Graph gra,String str,int hopcount) {
		Queue<Integer> que = new LinkedList<>();
		int M = findnum(test, str, 0, count);
		que.add(M); //큐 처음에 str의 숫자 삽입.
		while(!que.isEmpty()) {
			int node = que.poll();
			sz--;
			System.out.println(test[node][0]+" "+test[node][1]+ " "+ test[node][2]);
			hopc[node] =1;
			for(int i=0; i<gra.getNode(node).size();i++) {
				int t = gra.getNode(node).get(i);
				if(hopc[t]==1) {
					continue;
				}
			     hopc[t] = 1;
				 que.add(t); 
			}
			if(sz ==0) {
				sz = que.size();
				ans++;
			}
			if(ans >10) {				
				return;
			}
		}
		return;
		
		
	}
	
	//////////////////////////////////////////////////////////거리 계산
	
	
	public double calDistance(double lat1, double lon1, double lat2, double lon2) {
		 double theta, dist;
		 theta = lon1 - lon2;
		 dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
		 + Math.cos(deg2rad(lat1))
		 * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		 dist = Math.acos(dist);
		 dist = rad2deg(dist);
		 dist = dist * 60 * 1.1515;
		 dist = dist * 1.609344; // 단위 mile 에서 km 변환.
		 dist = dist * 1000.0; // 단위 km 에서 m 로 변환
		 return dist;
		 }
		 // 주어진 도(degree) 값을 라디언으로 변환
		 private double deg2rad(double deg) {
		 return (double)(deg * Math.PI / (double)180);
		 }
		 // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
		 private double rad2deg(double rad) {
		 return (double)(rad * (double)180 / Math.PI);
		 } 

}

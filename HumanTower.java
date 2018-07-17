import java.util.HashMap;
import java.util.*;
import java.io.*;
public class HumanTower{
   public static final boolean FILE_ERRORS=false;
   public static final int MAX_SIZE=100000;
   public static void main(String args[])throws IOException{
      testBaseball();
      
   }
   public static void knownTest(){
   List<Person> people=new ArrayList<Person>(){{
      add(new Person("a",1,0));
      add(new Person("a",2,8));
      add(new Person("a",3,4));
      add(new Person("a",4,12));
      add(new Person("a",5,2));
      add(new Person("a",6,10));
      add(new Person("a",7,6));
      add(new Person("a",8,14));
      add(new Person("a",9,1));
      add(new Person("a",10,9));
      add(new Person("a",11,5));
      add(new Person("a",12,13));
      add(new Person("a",13,3));
      add(new Person("a",14,11));
      add(new Person("a",15,7));
      add(new Person("a",16,15));/**/
      
      
      //0,2,6,9,11,15
      }};
     
      
      
      
      
      PrintOut(LargestTower(people));
   }
   
   public static void testBaseball()throws IOException{
     List<Person> people=new ArrayList<Person>();
      Scanner in=new Scanner(new File("mlb_players.csv"));
      in.nextLine();
      while(in.hasNext()){
         if(people.size()>=MAX_SIZE)break;
         String record=in.nextLine();
         String[]fields=record.split(",");
         if(fields.length==6){
            try{
               Person p=new Person(fields[0],Integer.parseInt(fields[3].trim()),Integer.parseInt(fields[4].trim()));
               people.add(p);
            }catch(NumberFormatException e){
               if(FILE_ERRORS)System.err.printf("Could not add \"%s\"\n",fields[0]);
            }
         }else if(fields.length>0){
            if(FILE_ERRORS)System.out.printf("\"%s\" has the wrong number of fields\n",fields[0]);
         }
      }
      in.close();
      
      
      PrintOut(LargestTower(people));
   }
   
   
   
   
   
   
   
   
   
   public static List<Person> LargestTower(List<Person> people){
      Collections.sort(people,(p1,p2)->p1.height-p2.height==0?p1.weight-p2.weight:p1.height-p2.height);
      people=filter(people);
      HashMap<Integer,LinkedList<Person>>sequence=new HashMap<Integer,LinkedList<Person>>();
      LinkedList<Person> list=new LinkedList<Person>();
      list.add(people.get(0));
      sequence.put(1,list);
      int max=1;
     
      for(Person p:people){
        for(int i=1;i<=max;i++){
            LinkedList<Person> current=sequence.get(i);
            Person first=current.getFirst();
            if(p.weight<first.weight&&(current.size()==1||p.weight>current.get(1).weight)){
               current.poll();
               current.offerFirst(p);
            }else if(p.weight>first.weight){
               LinkedList<Person> toAdd=new LinkedList<Person>();
               toAdd.addAll(current);
               toAdd.offerFirst(p);
               if(sequence.get(i+1)==null){
                  sequence.put(i+1,toAdd);
                  max=max>i+1?max:i+1;
               }else if(sequence.get(i+1).getFirst().weight>p.weight){
                  sequence.put(i+1,toAdd);
               }
            }
            
                     
            
         }
         
         
      }
      
      
   
      return sequence.get(max);
   }
   public static List<Person> filter(List<Person> people){
      ArrayList<Person> filtered=new ArrayList<Person>();
      Person prev=null;
      for(Person p:people){
         if(prev==null){
            filtered.add(p);
         }else if(p.height>prev.height){
            filtered.add(p);
         }
         prev=p;
      }
      return filtered;
   }
   
   

   
   
   
   
   
   
   
   
   public static void PrintOut(List<Person> people){
      if(people==null){
         System.out.println("null");
      }else{
         for(Person p:people){
            System.out.println(p);
         }
      }
   }
   
   
   public static boolean isValidTower(List<Person> people){
      Person prev=null;
      for(Person p:people){
         if(prev!=null&&(prev.height>=p.height||prev.weight>=p.weight)){
             return false;
         }
         prev=p;
      }
      return true;
   }
   
   
   
   
   private static class Person{
      private String name;
      private int height,weight;
      public Person(String name,int height, int weight){
      this.height=height;
      this.weight=weight;
      this.name=name;
      }
      
      public boolean equals(Object other){
         if(other instanceof Person){
            Person p2=(Person)other;
            return p2.height==this.height&&p2.weight==this.weight;
         }
         return false;
      }
      public int hashCode(){
         return this.height<<16+this.weight;
      }
      public String toString(){
         return String.format("%20s:(%4d,%4d)",name,height,weight);
      }
   
   }
}

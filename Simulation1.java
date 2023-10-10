/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation1;
import java.util.Random;
import java.util.*;
import java.lang.Math;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
public class Simulation1 {

 static double stream1() {
    double rand = Math.random();
    rand =  Math.log(1-rand)/(-5.0/60);
    return rand;
  }
  static double stream2() {
    double rand = Math.random();
    rand =  Math.log(1-rand)/(-15.0/60);
    return rand;
  }
  static double servicetime() {
    double rand = Math.random();
     rand =  Math.log(1-rand)/(-12.0/60);
    return rand;
  }
    public static void main(String[] args) {
      ArrayList<object> fel = new ArrayList<object>(); // Future Events List Arrivals
    ArrayList<object> del = new ArrayList<object>(); // Delayed Events List
    ArrayList<object> server = new ArrayList<object>(); // Ser`ved list
    double t = 0.0, newtime = 0.0;
    boolean serv1 = false, serv2 = false; // server states, false = idle
    int count = 0, streamType = -1, streamcount1 = 1, streamcount2 = 1;
    int index = -1;
   double waitingtime=0.0; double deltime=0.0; double nexttime=0.0;
   double z=servicetime();
    
    object x1 = new object(1, stream1(), 1 , "A");
    fel.add(x1);
    object x2 = new object(2, stream2(), 2 , "A");
    fel.add(x2);
    count = 2;
      //     waitingtime=  newtime+z;
    while (count <=50) {  
        System.out.println("\t -----------new event------------");
      System.out.println("customer "+count+ "\tcurrent time: " + t + "\tFEL:" + fel.size()
                       + "\tDEL: " + del.size() + "\tTotal Served: " + server.size());
 
      if (serv1) System.out.println("Server 1 busy");// boolean 
      else  System.out.println("Server 1 idle");
      if (serv2) System.out.println("Server 2 busy");
      else  System.out.println("Server 2 idle");

      if (fel.size() > 0) { // fel index of the smallest time;
        index = 0;
        for(int i=0; i < fel.size(); ++i) { //+1
            if (fel.get(i).time < fel.get(index).time) index = i; }
      }
   
  System.out.println("FEL:");
       for(int i=0; i < fel.size(); ++i
               )
           fel.get(i).print(); 
      System.out.println("DEL:----");
      if (del.size() < 5) 
          for(int i=0; i < del.size(); ++i)
              
              del.get(i).print();
      
      else for(int i=0; i < 5; ++i) 
          del.get(i).print();
      if (fel.get(index).status == "D") {
          fel.get(index).status = "Served";
            if (fel.get(index).streamType == 1) 
                serv1 = false; //if departure, service will be idle离开
            else if  (fel.get(index).streamType == 2) 
                serv2 = false; 

        server.add(fel.get(index));
        fel.remove(index);
        
        t = fel.get(index).time; 
        System.out.println("\nnow,airport departure  which is:");
        fel.get(index).print();
        fel.get(index).served = true;
        
      }
      else if (fel.get(index).status == "A" && (serv1 == true && serv2 == true)) {
// when arrival and service isbusy
        t = fel.get(index).time; 
       System.out.println("\nbusy and send to delaylist");
        fel.get(index).print();

        streamType = fel.get(index).server; // stream 1 or 2 toggle 
        if (streamType == 1) 
            ++streamcount1;
        else if (streamType == 2)
            ++streamcount2;         
        ++count;
        
        if (streamType == 1 ) 
            newtime = t + stream1();
        else if (streamType == 2) 
            newtime = t + stream2();
       
        System.out.println("\n\tNew customer:");
         object x = new object(count, newtime, streamType , "A");
       x.print();
        
        fel.get(index).delayed = true;
        del.add(fel.get(index)); // no delayed list
        fel.remove(index);
        fel.add(x);
      }

      else if (fel.get(index).status == "A" && del.size() ==  0 && (serv1 == false || serv2 == false)) {// arrival, idle
        t = fel.get(index).time; 
        System.out.println("\t Assigning stream ");
       fel.get(index).print();
        fel.get(index).service(t +  z); 
   
        if (serv1 == false) { 
          serv1 = true; fel.get(index).streamType = 1;//delay
        } 
        else if (serv2 == false) { 
          serv2 = true; fel.get(index).streamType = 2;
        }

   //     System.out.println("\t Assigning stream " + fel.get(index).streamType);
        streamType = fel.get(index).server; // stream 1 or 2 toggle 
        if (streamType == 1){
            ++streamcount1;
            newtime = t + stream1();

        }
        else if (streamType == 2) {
            ++streamcount2;
                newtime = t + stream2();

                }
     
       System.out.println("Departure info:");
        fel.get(index).print();
        ++count;     

        System.out.println("\n\tNew customer:");
                object x = new object(count, newtime, streamType , "A");
        x.print();

        if (fel.size() < 4) fel.add(x);
      }   
      else if (fel.get(index).status == "A" && del.size() >  0
           && (serv1 == false || serv2 == false)) {               //delaylist 
      System.out.println("\nServer idle getting customer from DEL for departure:");
        del.get(0).print();
   
        if (serv1 == false) { // server toggle and "assignment"
          serv1 = true; del.get(0).streamType = 1;//change busy 
        }
        else if (serv2 == false) {
          serv2 = true; del.get(0).streamType = 2;
        } 
      /*     System.out.println( " Average idle time:");
        System.out.println("\t Assigning stream " + del.get(0).streamType);*/
        del.get(0).service(t + z); //delay 
     /*   System.out.println("\nDeparture info:");
        del.get(0).print(); */
         waitingtime=del.get(0).time;
        fel.add(del.get(0)); // adding departure object fel
        del.remove(0); 
        }
      
 
       nexttime=fel.get(0).time;
                       System.out.println("====================");
                     System.out.println("stream 1 input speed  :"+stream1());
                      System.out.println("stream 2 input speed  :"+stream2());
                        System.out.println("avg for service time :"+servicetime());//customer
               System.out.println("avg interarrival rate :"+(nexttime-t));
                 System.out.println("avg waiting time for each customer"+waitingtime);
    
    }
     
///-------

    
     
    System.out.println("\n-----------------------------------------------------");

    System.out.println("\n\t\t---END OF SIMULATION---");
    System.out.println("\nfel: " + fel.size() + "\tdel: " + del.size() + "\tsvd: " + server.size());
    System.out.println("\nstream1 count\t" + streamcount1 + "\nstream2 count\t" + streamcount2);
      
    System.out.println("\n-----------------------------------------------------");
  }
    }
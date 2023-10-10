/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation1;

/**
 *
 * @author heten
 */
public class object {
int customerNum = 0;
  double time = 0.0;
  int  server = 0;
  int streamType = 0;
  String status = "empty"; //Arival, Departure, or Served --> A, D, or SVD
  boolean depart = true;
  boolean served = true;
  boolean delayed = true;
  public object(int n, double t, int stream, String a) {
    customerNum = n;
    time = t;
     server = stream;
    status = a;
  }
  public void service (double newtime) {
    this.now();
    time = newtime;
    depart = false;
    status = "D";
    this.newcustomer();
  }
  public void print() {
    System.out.println("[ status:" + status + ", customernum= " + customerNum + ","+" stream=" + server + ","+"time=" + time + " ]");
  }
  public void now() {
    System.out.println("\n[NOW: " + "status:"+ status + ", customernum= " + customerNum + ", time=" + time +" ]");
  }
  public void newcustomer() {
    System.out.println("[new: " + " status:"+status + ", customernum= "+ customerNum + ", time=" + time +"]");
  }
}
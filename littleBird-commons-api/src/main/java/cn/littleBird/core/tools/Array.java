package cn.littleBird.core.tools;


import java.util.*;

/**
 * T必须重新equals方法  T不能为基本数据类型
 * @param <T>
 */
public class Array<T> {
  private ArrayList<T> arr;

  public Array(ArrayList<T> arr) {
	this.arr = arr;
  }

 	//add in first
    public void unshift(T value){
	  this.arr.add(0, value);
    }
    //add in last
    public void push(T value){
	  this.arr.add(value);
    }
    public void shift(){
	  this.arr.remove(0);
    }
    public void pop(){
	  this.arr.remove(this.arr.size() - 1);
    }
    public void removeElments(T val){
      if(val == null)
        return;
        for (int i = 0; i < this.arr.size(); i++) {
		  if(val.equals(this.arr.get(i)))
		    this.arr.remove(i);
        }
    }
    public void print(){
	  Iterator<T> it = this.arr.iterator();
	  System.out.print("\\[");
	  while(it.hasNext()) {
		System.out.print(it.next() + ",");
	  }
	  System.out.println("\\]");
	  System.out.println();
	}
}

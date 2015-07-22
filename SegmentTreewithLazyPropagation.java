package segmenttreewithlazypropogation;
import java.util.*;
import java.io.*;
public class SegmentTreewithLazyPropogation
{
    static int lazy[]=new int[1000001];
    static Scanner sc=new Scanner(System.in);
    static int nextPower(int a)
    {
        a--;
        a=a|a<<1;
        a=a|a<<2;
        a=a|a<<4;
        a=a|a<<8;
        a=a|a<<16;
        a++;
        return a;
    }
    static void csegt(int []segmenttree,int []input,int low,int high,int pos)
    {
        if(low==high)
        {
            segmenttree[pos]=input[low];
            return;
        }
        int mid=(low+high)/2;
        csegt(segmenttree,input,low,mid,2*pos+1);
        csegt(segmenttree,input,mid+1,high,2*pos+2);
        segmenttree[pos]=Math.max(segmenttree[2*pos+1], segmenttree[2*pos+2]);
    }
    static void updatequery(int input[],int segmenttree[],int index,int value)
    {
        input[index]=value;
        updatequery(segmenttree,index,0,input.length-1,0,value);
    }
    static void updatequery(int segmenttree[],int index,int low,int high,int pos,int value){
        if(lazy[pos] != 0) { // This node needs to be updated
        segmenttree[pos] += lazy[pos]; // Update it

        if(low != high) {
            lazy[pos*2+1] += lazy[pos]; // Mark child as lazy
                lazy[pos*2+2] += lazy[pos]; // Mark child as lazy
        }

        lazy[pos] = 0; // Reset it
    }
        if(index<low||index>high)
            return;
        if(low==high)
        {
            segmenttree[pos]=value;
            return;
        }
        int mid=(low+high)/2;
        updatequery(segmenttree,index,low,mid,2*pos+1,value);
        updatequery(segmenttree,index,mid+1,high,2*pos+2,value);
        segmenttree[pos]=Math.max(segmenttree[2*pos+1],segmenttree[2*pos+2]);
    }
    
    static int rangeMaxquery(int []segmenttree,int qlow,int qhigh,int len)
    {
         return rangeMaxquery(segmenttree,0,len-1,qlow,qhigh,0);
    }
    static int rangeMaxquery(int []segmenttree,int low,int high,int qlow,int qhigh,int pos)
    {
        if(qlow<=low&&qhigh>=high)
        {
            return segmenttree[pos];
        }
        if(qlow>high||qhigh<low)
        {
            return Integer.MIN_VALUE;
        }
        if(lazy[pos]!=0)
        {
            segmenttree[pos]+=lazy[pos];//update
            if(low!=high)//not the leaf node
            {
                lazy[2*pos+1]+=lazy[pos];//mark child as lazy
                lazy[2*pos+2]+=lazy[pos];//mark child as lazy
            }lazy[pos]=0;//reset
        }
        int mid=(low+high)/2;
        return (Math.max(rangeMaxquery(segmenttree,low,mid,qlow,qhigh,2*pos+1),rangeMaxquery(segmenttree,mid+1,high,qlow,qhigh,2*pos+2)));
    }
    static int[] createsegmentTree(int []input)
    {
        int nextpowerof2=nextPower(input.length);
        int []segmentTree=new int[nextpowerof2*2-1];
        for(int i=0;i<segmentTree.length;i++)
        {
            segmentTree[i]=Integer.MIN_VALUE;
        }
        for(int i=0;i<segmentTree.length*2;i++)
        {
            lazy[i]=0;
        }
        csegt(segmentTree,input,0,input.length-1,0);
        return segmentTree;
    }
    public static void main(String[] args) {
        int a;
        System.out.println("enter the no of leaves in a node");
        a=sc.nextInt();
        int input[]=new int[a];
        for(int i=0;i<a;i++)
        {
            input[i]=sc.nextInt();
        }
    }
    
}

package net.sf.memoranda.util;

import nu.xom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class PriorityQueue.
 */
public class PriorityQueue {
        
        /* Cola de Prioridad implementada con Estructura de Datos Heap,
         * para ordenar las anotaciones por prioridad 
         */
        
        /** The a. */
        private Pair[] a;
        
        /** The n. */
        private int n;
        
        /**
         * Instantiates a new priority queue.
         *
         * @param size the size
         */
        public PriorityQueue(int size){
                a = new Pair[size+2];
                n = 0;
        }
        
        /**
         * Insertar.
         *
         * @param x the x
         */
        public void insertar(Pair x){
                ++n;
                a[n]=x;
                for(int j=n; j>1 && a[j].getPriority() < a[j/2].getPriority(); j/=2)
                {
                        Pair t = a[j];
                        a[j] = a[j/2];
                        a[j/2] = t;
                }
        }
        
        /**
         * Extraer.
         *
         * @return the element
         */
        public Element extraer(){
                if(!this.Vacia()){
                        Element m = a[1].getElement();  
                        a[1] = a[n];
                        --n;
                        int j = 1;
                        while(2*j<=n)
                        {
                                int k=2*j;
                                if(k+1<=n && a[k+1].getPriority() < a[k].getPriority())
                                        k=k+1;  
                                if(a[j].getPriority() < a[k].getPriority())
                                        break;
                                Pair t = a[j]; 
                                a[j] = a[k];
                                a[k] = t;  
                                j = k;
                        }
                        return m;
                }
                else 
                        return null;
        }
        
        /**
         * Vacia.
         *
         * @return true, if successful
         */
        public boolean Vacia(){
                return n==0;
        }

}
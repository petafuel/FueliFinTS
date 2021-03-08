package net.petafuel.fuelifints.support;

import java.util.Iterator;

/**
 * Description: Stringliste ohne generics, die daher fixer arbeitet als die "original" Java Variante
 */
public class StringList implements Iterable<String> {

    int size = 0;
    private String[] array = new String[16];

    public String get(int i){
        return array[i];
    }

    public void add(String b){
        array[size] = b;
        size++;
        if(size >= array.length){
            String[] temp = new String[array.length*2];
            System.arraycopy(array,0,temp,0,array.length);
            array = temp;
            temp = null;
        }

    }

    private int size() {
        return size;
    }

    public String[] getArray(){
        String[] temp = new String[size()];
        System.arraycopy(array,0,temp,0,size());
        return temp;
    }

    public Iterator<String> iterator() {
        return new StringListIterator(this);  //To change body of implemented methods use File | Settings | File Templates.
    }

    private class StringListIterator implements Iterator<String> {
        private final StringList stringList;
        private int position = 0;

        public StringListIterator(StringList stringList) {
            this.stringList = stringList;
        }

        public boolean hasNext() {
            return position < stringList.size();
        }

        public String next() {
            return stringList.get(position++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

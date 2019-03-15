package my_Taxi;

public interface BiIterator <Etype>{
	Etype next();
	Etype previous();
	boolean hasNext();
	boolean hasPrevious();
}

import java.util.*;
import java.io.*;

/**
	This class is provided by the instructor to test some of the
	basic functionality of the Store class.
*/
public class StoreTest
{
   public static void main(String[] args)
   {
		Store myStore = new Store();
      String output = myStore.process("trans0.txt");
      System.out.println(output);
   }
}



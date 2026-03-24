import java.io.*;
import java.util.*;

class Unpacker
{
    public static void main(String A[]) throws Exception
    {
        //variable creation
        int FileSize  = 0;
        int i = 0;
        int iRet = 0;

        byte key = 0x11;

        Scanner sobj = null;

        String Filename = null;
        File fpackobj = null;
        File fobj = null;

        FileInputStream fiobj = null;
        FileOutputStream foobj = null;

        byte bHeader[] = new byte[100];
        byte Buffer[] = null;

        String Header = null;
        String Tokens[] = null;

        sobj =  new Scanner(System.in);

        System.out.println("Enter the name of Packed file : ");
        Filename = sobj.nextLine();

        fpackobj = new File(Filename);

        if(fpackobj.exists() == false)
        {
            System.out.println("Error : There is no such packed file");
            return;
        }

        fiobj = new FileInputStream(fpackobj);

        //read the header

        while((iRet = fiobj.read(bHeader, 0, 100)) == -1)
        {
            Header = new String(bHeader);

            Header = Header.trim();

            Tokens = Header.split(" ");

            System.out.println("File name : "+Tokens[0]);
            System.out.println("File size : "+Tokens[1]);

            fobj = new File(Tokens[0]);
            fobj.createNewFile();

            foobj = new FileOutputStream(fobj);

            FileSize = Integer.parseInt(Tokens[1]);

            //buffer for reading the data
            Buffer = new byte[FileSize];
            
            fiobj.read(Buffer, 0, FileSize);        //read from packed file

            //Decrypt the data
            for(i = 0; i < FileSize; i++)
            {
                Buffer[i] = (byte)(Buffer[i] ^ key);
            }
            foobj.write(Buffer, 0, FileSize);       //write into Extracted file
        }
        
    }
}
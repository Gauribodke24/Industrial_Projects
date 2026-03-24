import java.io.*;
import java.util.*;

class Packer
{
    public static void main(String A[]) throws Exception
    {
        String Header = null;
        byte key = 0x11;

        int iRet = 0;
        int i = 0, j = 0;
        byte Buffer[] = new byte[1024];

        byte bHeader[] = new byte[100];

        Scanner sobj = new Scanner(System.in);

        System.out.println("Enter the name of folder : ");
        String Foldername = sobj.nextLine();

        System.out.println("Enter the name of packed file : ");
        String Packname = sobj.nextLine();

        File fobj = new File(Foldername);
        
        if((fobj.exists()) && (fobj.isDirectory()))
        {
            File PackObj = new File(Packname);

            PackObj.createNewFile();

            FileOutputStream foobj = new FileOutputStream(PackObj);

            FileInputStream fiobj = null;

            System.out.println("Folder is present");

            File fArr[] = fobj.listFiles();

            System.out.println("Number of file in the folder are : "+fArr.length);

            for(i = 0; i < fArr.length; i++)
            {
                fiobj = new FileInputStream(fArr[i]);

                if(fArr[i].getName().endsWith(".txt"))
                {
                    Header = fArr[i].getName() +" "+fArr[i].length();

                    for(j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }
                    
                    bHeader = Header.getBytes();                       

                    foobj.write(bHeader, 0, 100);
                    
                    while((iRet = fiobj.read(Buffer)) != -1)
                    {
                        for(j = 0; j < iRet; j++)
                        {
                            Buffer[j] = (byte)(Buffer[j] ^ key);
                        }

                        foobj.write(Buffer, 0, iRet);
                    }
                }
                fiobj.close();
            }
            foobj.close();
        }
        else
        {
            System.out.println("There is no such folder");
        }

        sobj.close();
    }
}
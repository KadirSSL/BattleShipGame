import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BattleShipGame {
    public static void matrisOlustur(char[][] matris)
    {
        for (int i = 0; i < matris.length; i++)
        {
            for (int j = 0; j < matris[i].length; j++)
            {
                matris[i][j] = '*';
            }
        }
    }

    public static void rastgeleGemileriYerlestir(char[][] matris,int gemi)
    {
        Random rand = new Random();
        int boyut = matris.length;


        int yon = rand.nextInt(2);

        if (yon==0)
        {
            int x = rand.nextInt(boyut);
            int y = rand.nextInt(boyut);

            for(int h=0; h<gemi; h++)
            {
                if(x+h>boyut)
                {
                    rastgeleGemileriYerlestir(matris,gemi);
                    return;
                }
            }
            for(int h=0; h<gemi; h++)
                matris[x+h][y]=Character.forDigit(gemi, 10);
        }
        else
        {
            int x = rand.nextInt(boyut);
            int y = rand.nextInt(boyut);

            for(int h=0; h<gemi; h++)
            {
                if(y+h>boyut)
                {
                    rastgeleGemileriYerlestir(matris,gemi);
                    return;
                }
            }
            for (int h=0; h<gemi; h++)
                matris[x][y+h]= Character.forDigit(gemi, 10);
        }
    }


    public static void oyunuOyna(char[][] oyuncuMatrisi, char[][] bilgisayarMatrisi, Scanner scanner, int amiralGemisiSayaci,int amiral2) {
        while (true)
        {
            // Oyuncu veya bilgisayar atış yapacak
            int atis = (int) (Math.random() * 2); // 0: Bilgisayar, 1: Oyuncu

            if (atis == 0)
            {
                // Bilgisayarın atış yapması
                int sutun = (int) (Math.random() * oyuncuMatrisi.length);
                int satir = (int) (Math.random() * oyuncuMatrisi[0].length);

                if(Character.isDigit(oyuncuMatrisi[satir][sutun]))
                {
                    if(oyuncuMatrisi[satir][sutun]=='5')
                    {
                        System.out.println("Bilgisayar amiral gemisini vurdu!");
                        oyuncuMatrisi[satir][sutun] = 'X';
                        amiralGemisiSayaci--;
                    }

                    else{
                        System.out.println("Bilgisayar normal bir gemiyi vurdu!");
                        oyuncuMatrisi[satir][sutun] = 'O';
                    }
                }
                if(oyuncuMatrisi[satir][sutun]=='*')
                {
                    System.out.println("Bilgisayar boş yeri vurdu!");
                    oyuncuMatrisi[satir][sutun] = '-';
                }
            }

            else
            {
                // Oyuncunun atış yapması
                System.out.print("Atış yapmak istediğiniz koordinatı girin (örnek: A3): ");
                String giris = scanner.next();
                char harf = Character.toUpperCase(giris.charAt(0));
                int satir = giris.charAt(1)-'1';
                if(giris.length()==3){
                    satir=giris.charAt(2)-'1'+10;
                }

                int sutun = harf - 'A';

                if(Character.isDigit(bilgisayarMatrisi[satir][sutun]))
                {
                    if(bilgisayarMatrisi[satir][sutun]=='5')
                    {
                        System.out.println("Amiral gemisini vurdunuz!");
                        bilgisayarMatrisi[satir][sutun] = 'X';
                        amiral2--;
                    }

                    else
                    {
                        System.out.println("Normal bir gemiyi vurdunuz!");
                        bilgisayarMatrisi[satir][sutun] = 'O';
                    }
                }
                if(bilgisayarMatrisi[satir][sutun]=='*')
                {
                    System.out.println("Boş yeri vurdunuz!");
                    bilgisayarMatrisi[satir][sutun] = '-';
                }
            }

            // Oyun durumu kontrolü
            if (amiralGemisiSayaci == 0)
            {
                System.out.println("malesef kaybettiniz");
                break;
            }
            if (amiral2 == 0)
            {
                System.out.println("Tebrikler! Amiral gemiyi batırdınız. Oyunu kazandınız!");
                break;
            }

            // Diğer oyuncunun sırası
            System.out.println("\nSıra diğer oyuncuda...");
            System.out.println("Oyuncunun Matrisi:");
            matrisYazdir(oyuncuMatrisi);

            System.out.println("Bilgisayarın Matrisi:");
            matrisYazdir(bilgisayarMatrisi);
        }
    }


    public static void matrisYazdir(char[][] matris)
    {
        System.out.print("  ");
        for (int i = 0; i < matris.length; i++)
        {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < matris.length; i++)
        {
            System.out.print((i+1) + " ");
            for (int j = 0; j < matris[i].length; j++)
            {
                System.out.print(matris[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] gemiler={2,2,2,3,3,5};

        System.out.println("Amiral Battı Oyununa Hoşgeldiniz!");
        System.out.println("Lütfen oynamak istediğiniz seviyeyi seçin:");
        System.out.println("1 - Kolay (10x10 matris)");
        System.out.println("2 - Zor (14x14 matris)");

        int seviye = scan.nextInt();
        int boyut = (seviye == 1) ? 10 : 14;

        char[][] oyuncuMatrisi = new char[boyut][boyut];
        char[][] bilgisayarMatrisi = new char[boyut][boyut];

        matrisOlustur(oyuncuMatrisi);
        matrisOlustur(bilgisayarMatrisi);
        for(int gemi:gemiler)
        {
            rastgeleGemileriYerlestir(oyuncuMatrisi,gemi);
            rastgeleGemileriYerlestir(bilgisayarMatrisi,gemi);
        }
        System.out.println("Oyun başlıyor...");
        int amiralGemisiSayaci = 5 ;
        int amiral2=5;

        oyunuOyna(oyuncuMatrisi, bilgisayarMatrisi, scan, amiralGemisiSayaci,amiral2);

    }

}
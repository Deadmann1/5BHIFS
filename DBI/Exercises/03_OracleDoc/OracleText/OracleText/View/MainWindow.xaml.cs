using OracleText.Model;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace OracleText.View
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private const string symmetricEncryptedFilename = "symmetricEncrypted.html";
        private const string asymmetricEncryptedFilename = "asymmetricEncrypted.html";
        private const string temporaryFileForOpeningFilename = "temp.html";

        private OracleTextDatabaseManager OracleTextDatabaseManager;

        public MainWindow (OracleTextDatabaseManager oracleTextDatabaseManager)
        {
            InitializeComponent();

            OracleTextDatabaseManager = oracleTextDatabaseManager;
        }

        private void BtnHTML_Click(object sender, RoutedEventArgs e)
        {
            Document selectedDocument = (Document) DtgrdResults.SelectedItem;
            String fullPathAndName = SaveSymmetricEncryptedDocumentLocally(selectedDocument);
            String decryptedFileAndName = SymmetricallyDecryptFile(fullPathAndName);

            OpenDocumentInNewWpfWindow(decryptedFileAndName);
            //OpenDocumentInBrowser(decryptedFileAndName);
        }

        private String SymmetricallyDecryptFile(String fullPathAndName)
        {
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            BinaryFormatter deserializer = new BinaryFormatter();
            SymmetricallyEncryptedFile encryptedFile;
            String decryptedFilePath = Directory.GetCurrentDirectory() + "/" + temporaryFileForOpeningFilename;
            String decryptedFileData;

            using (FileStream fileStream = new FileStream(fullPathAndName, FileMode.Open))
            {
                encryptedFile = deserializer.Deserialize(fileStream) as SymmetricallyEncryptedFile;

                rijndaelManaged.Key = encryptedFile.Key;
                rijndaelManaged.IV = encryptedFile.IV;
                rijndaelManaged.Padding = PaddingMode.Zeros;
            }

            using (MemoryStream memoryStream = new MemoryStream(encryptedFile.Content))
            {
                using (FileStream fileStream = new FileStream(decryptedFilePath, FileMode.Create))
                {
                    using (CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateDecryptor(), CryptoStreamMode.Read))
                    {
                        using (StreamReader streamReader = new StreamReader(cryptoStream))
                        {
                            decryptedFileData = streamReader.ReadToEnd();
                        }
                    }
                }
            }

            File.WriteAllText(decryptedFilePath, decryptedFileData);

            return decryptedFilePath;
        }

        private void OpenDocumentInBrowser(string fullPathAndName)
        {
            Process.Start(fullPathAndName);
        }

        private void OpenDocumentInNewWpfWindow(string fullPathAndName)
        {
            new DisplayHtmlFileWindow(fullPathAndName).Show();
        }

        private string SaveSymmetricEncryptedDocumentLocally(Document selectedDocument)
        {
            String filePath = Directory.GetCurrentDirectory() + "/" + symmetricEncryptedFilename;
            MemoryStream memoryStream = new MemoryStream();
            RijndaelManaged rijndaelManaged = new RijndaelManaged();

            rijndaelManaged.Padding = PaddingMode.Zeros;

            CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateEncryptor(), CryptoStreamMode.Write);
            StreamWriter streamWriter = new StreamWriter(cryptoStream);
            BinaryFormatter serializer = new BinaryFormatter();
            SymmetricallyEncryptedFile encryptedFile = new SymmetricallyEncryptedFile();      

            streamWriter.Write(selectedDocument.Content);
            streamWriter.Flush();

            memoryStream.Close();

            encryptedFile.Content = memoryStream.ToArray();
            encryptedFile.IV = rijndaelManaged.IV;
            encryptedFile.Key = rijndaelManaged.Key;

            using (FileStream fileStream = new FileStream(filePath, FileMode.Create))
            {
                serializer.Serialize(fileStream, encryptedFile);
            }

            return filePath;
        }

        private void BtnRetrieve_Click(object sender, RoutedEventArgs e)
        {
            String searchString = TxtSearchString.Text;

            DtgrdResults.ItemsSource = OracleTextDatabaseManager.GetDocumentsContainingString(searchString);
        }

        private void BtnSaveSymmetricEncrypted_Click(object sender, RoutedEventArgs e)
        {
            Document selectedDocument = (Document)DtgrdResults.SelectedItem;

            SaveSymmetricEncryptedDocumentLocally(selectedDocument);
        }

        private void BtnOpenSymmetricEncrypted_Click(object sender, RoutedEventArgs e)
        {
            try
            { 
                String decryptedFileAndName = SymmetricallyDecryptFile(Directory.GetCurrentDirectory() + "/" + symmetricEncryptedFilename);

                OpenDocumentInNewWpfWindow(decryptedFileAndName);
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("No asymmetrically encrypted file available!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void BtnSaveAsymmetricEncrypted_Click(object sender, RoutedEventArgs e)
        {
            Document selectedDocument = (Document)DtgrdResults.SelectedItem;

            SaveAsymmetricEncryptedDocumentLocally(selectedDocument);
        }

        private void SaveAsymmetricEncryptedDocumentLocally(Document selectedDocument)
        {
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            RSACryptoServiceProvider rsa = new RSACryptoServiceProvider();
            AsymmetricallyEncryptedFile encryptedFile = new AsymmetricallyEncryptedFile();
            String filePath = Directory.GetCurrentDirectory() + "/" + asymmetricEncryptedFilename;
            MemoryStream memoryStream = new MemoryStream();
            CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateEncryptor(), CryptoStreamMode.Write);
            StreamWriter streamWriter = new StreamWriter(cryptoStream);
            BinaryFormatter serializer = new BinaryFormatter();

            rijndaelManaged.Padding = PaddingMode.Zeros;

            streamWriter.Write(selectedDocument.Content);
            streamWriter.Flush();

            memoryStream.Close();

            encryptedFile.Content = memoryStream.ToArray();
            encryptedFile.IV = rsa.Encrypt (rijndaelManaged.IV, false);
            encryptedFile.Key = rsa.Encrypt(rijndaelManaged.Key, false);
            
            RSAParameters rsaParameters = rsa.ExportParameters(true);
            RSAParametersSerializable rsaParametersSerializable = new RSAParametersSerializable();

            rsaParametersSerializable.D = rsaParameters.D;
            rsaParametersSerializable.DP = rsaParameters.DP;
            rsaParametersSerializable.DQ = rsaParameters.DQ;
            rsaParametersSerializable.Exponent = rsaParameters.Exponent;
            rsaParametersSerializable.InverseQ = rsaParameters.InverseQ;
            rsaParametersSerializable.Modulus = rsaParameters.Modulus;
            rsaParametersSerializable.P = rsaParameters.P;
            rsaParametersSerializable.Q = rsaParameters.Q;

            encryptedFile.RsaParameters = rsaParametersSerializable;

            using (FileStream fileStream = new FileStream(filePath, FileMode.Create))
            {
                serializer.Serialize(fileStream, encryptedFile);
            }
        }

        private void BtnOpenAsymmetricEncrypted_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                String decryptedFileAndName = AsymmetricallyDecryptFile();

                OpenDocumentInNewWpfWindow(decryptedFileAndName);
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("No asymmetrically encrypted file available!", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private string AsymmetricallyDecryptFile()
        {
            AsymmetricallyEncryptedFile encryptedFile;
            RSACryptoServiceProvider rsa = new RSACryptoServiceProvider();
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            BinaryFormatter deserializer = new BinaryFormatter();
            String decryptedFilePath = Directory.GetCurrentDirectory() + "/" + temporaryFileForOpeningFilename;
            String decryptedFileData;

            using (FileStream fileStream = new FileStream(Directory.GetCurrentDirectory() + "/" + asymmetricEncryptedFilename, FileMode.Open))
            {
                encryptedFile = (AsymmetricallyEncryptedFile) new BinaryFormatter().Deserialize(fileStream);
            }

            RSAParametersSerializable rsaParametersSerializable = encryptedFile.RsaParameters;
            RSAParameters rsaParameters = new RSAParameters();

            rsaParameters.D = rsaParametersSerializable.D;
            rsaParameters.DP = rsaParametersSerializable.DP;
            rsaParameters.DQ = rsaParametersSerializable.DQ;
            rsaParameters.Exponent = rsaParametersSerializable.Exponent;
            rsaParameters.InverseQ = rsaParametersSerializable.InverseQ;
            rsaParameters.Modulus = rsaParametersSerializable.Modulus;
            rsaParameters.P = rsaParametersSerializable.P;
            rsaParameters.Q = rsaParametersSerializable.Q;

            rsa.ImportParameters(rsaParameters);
            rijndaelManaged.Key = rsa.Decrypt(encryptedFile.Key, false);
            rijndaelManaged.IV = rsa.Decrypt(encryptedFile.IV, false);
            rijndaelManaged.Padding = PaddingMode.Zeros;

            using (MemoryStream memoryStream = new MemoryStream(encryptedFile.Content))
            {
                using (FileStream fileStream = new FileStream(decryptedFilePath, FileMode.Create))
                {
                    using (CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateDecryptor(), CryptoStreamMode.Read))
                    {
                        using (StreamReader streamReader = new StreamReader(cryptoStream))
                        {
                            decryptedFileData = streamReader.ReadToEnd();
                        }
                    }
                }
            }

            File.WriteAllText(decryptedFilePath, decryptedFileData);

            return Directory.GetCurrentDirectory() + "/" + temporaryFileForOpeningFilename;
        }
    }
}

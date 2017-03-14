namespace OracleText.Model
{
    public class Document
    {
        public int Id
        {
            get;
            set;
        }

        public string Title
        {
            get;
            set;
        }

        public string Content;

        public Document(int id, string title, string content)
        {
            Id = id;
            Title = title;
            Content = content;
        }
    }
}
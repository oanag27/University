//using System;
//using System.Collections.Generic;
//using System.ComponentModel;
//using System.Configuration;
//using System.Data;
//using System.Drawing;
//using System.Linq;
//using System.Text;
//using System.Threading.Tasks;
//using System.Windows.Forms;
//using System.Data.SqlClient;
//namespace prj1
//{
//    public partial class Form1 : Form
//    {
//        private string FormCaption;

//        private string connection;
//        SqlConnection c;
//        SqlDataAdapter daAuthors; //table authors
//        SqlDataAdapter daBooks; //table books
//        DataSet ds;
//        //BindingSource bsAuthors;
//        //BindingSource bsBooks;
//        SqlCommandBuilder cmdBuilder;
//        //string queryAuthors;
//        //string queryBooks;
//        private string oneString;
//        private string oneExists;
//        private string oneDataExists;
//        private string oneQuery;
//        private string queryOneFill;


//        private string manyString;
//        private string manyExists;
//        private string manyQuery;
//        private string manyDataExists;
//        private string queryManyFill;

//        private string oneToManyString;
//        private string oneToManyQuery;

//        private string key;
//        public Form1()
//        {
//            InitializeComponent();
//            this.FormCaption = ConfigurationManager.AppSettings["FormCaption"];
//            this.Text = FormCaption;
//            this.connection = ConfigurationManager.AppSettings["ConnectionString"];

//            this.oneQuery = ConfigurationManager.AppSettings["OneQuery"];
//            this.manyQuery = ConfigurationManager.AppSettings["ManyQuery"];
//            this.oneToManyQuery = ConfigurationManager.AppSettings["OneToManyQuery"];

//            this.oneString = ConfigurationManager.AppSettings["OneString"];
//            this.manyString = ConfigurationManager.AppSettings["ManyString"];
//            this.oneToManyString = ConfigurationManager.AppSettings["OneToManyString"];

//            this.key = ConfigurationManager.AppSettings["Key"];

//            this.oneExists = ConfigurationManager.AppSettings["OneExists"];
//            this.manyExists = ConfigurationManager.AppSettings["ManyExists"];

//            this.oneDataExists = ConfigurationManager.AppSettings["OneDataExists"];
//            this.manyDataExists = ConfigurationManager.AppSettings["ManyDataExists"];

//            CheckTablesExistence();
//            CheckData();


//            FillData();
//        }
//        void FillData() //fill form with data from db
//        {
//            try {
//                //connection = new SqlConnection(getConnectionString());
//                //queryAuthors = "SELECT * FROM Authors";
//                //queryBooks = "SELECT * FROM Books";

//                //daAuthors = new SqlDataAdapter(queryAuthors, connection);
//            //daBooks = new SqlDataAdapter(queryBooks, connection);
//            c = new SqlConnection(this.connection);
//            queryOneFill = this.oneQuery;
//            queryManyFill = this.manyQuery;
//            daAuthors = new SqlDataAdapter(queryOneFill, c);
//            daBooks = new SqlDataAdapter(queryManyFill, c);
//                ds = new DataSet();

//                //daAuthors.Fill(ds, "Authors");
//                //daBooks.Fill(ds, "Books");
//                daAuthors.Fill(ds, this.oneString);
//                daBooks.Fill(ds, this.manyString);

//                cmdBuilder = new SqlCommandBuilder(daBooks);

//                //ds.Relations.Add("AuthorsBooks",
//                //    ds.Tables["Authors"].Columns["author_id"],
//                //    ds.Tables["Books"].Columns["author_id"]);
//                ds.Relations.Add(this.oneToManyString, ds.Tables[this.oneString].Columns[key], ds.Tables[this.manyString].Columns[key]);


//                //method1
//                //fill data into datagridview
//                //dataGridView1.DataSource = ds.Tables["Authors"];
//                //dataGridView2.DataSource = dataGridView1.DataSource;
//                //dataGridView2.DataMember = "AuthorsBooks";
//                this.dataGridView1.DataSource = ds.Tables[this.oneString];
//                this.dataGridView2.DataSource = this.dataGridView1.DataSource;
//                this.dataGridView2.DataMember = this.oneToManyString;




//                //insert, update, delete commands via sqlCommandBuilder
//                cmdBuilder.GetUpdateCommand();
//            }catch (Exception ex)
//            {
//                MessageBox.Show("An error occurred while filling data: " + ex.Message);
//            }

//        }
//        private string getConnectionString()
//        {
//            return "Data Source=OMG\\MSSQLSERVER01;Initial Catalog=library;Integrated Security=True";
//        }

//        private void Form1_Load(object sender, EventArgs e)
//        {
//            this.FormCaption = ConfigurationManager.AppSettings["FormCaption"];
//            this.Text = FormCaption;
//            this.connection = ConfigurationManager.AppSettings["ConnectionString"];

//            this.oneQuery = ConfigurationManager.AppSettings["OneQuery"];
//            this.manyQuery = ConfigurationManager.AppSettings["ManyQuery"];
//            this.oneToManyQuery = ConfigurationManager.AppSettings["OneToManyQuery"];

//            this.oneString = ConfigurationManager.AppSettings["OneString"];
//            this.manyString = ConfigurationManager.AppSettings["ManyString"];
//            this.oneToManyString = ConfigurationManager.AppSettings["OneToManyString"];

//            this.key = ConfigurationManager.AppSettings["Key"];

//            this.oneExists = ConfigurationManager.AppSettings["OneExists"];
//            this.manyExists = ConfigurationManager.AppSettings["ManyExists"];

//            this.oneDataExists = ConfigurationManager.AppSettings["OneDataExists"];
//            this.manyDataExists = ConfigurationManager.AppSettings["ManyDataExists"];
//        }

//        private void label1_Click(object sender, EventArgs e)
//        {

//        }
//        private void button1_Click(object sender, EventArgs e)
//        {
//            try
//            {
//                daBooks.Update(ds, "Books");
//            }
//            catch (Exception ex)
//            {
//                MessageBox.Show("An error occurred while updating data: " + ex.Message);
//            }

//        }
//        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
//        {

//        }
//        void CheckTablesExistence()
//        {
//            //try
//            //{
//            //    c = new SqlConnection(this.connection);
//            //    c.Open();

//            //    // Check if authors table exists
//            //    SqlCommand cmdOne = new SqlCommand(this.oneExists, c);
//            //    object resultOne = cmdOne.ExecuteScalar();
//            //    if (resultOne == null)
//            //    {
//            //        MessageBox.Show("One table does not exist.");
//            //        // Handle accordingly, maybe create the table or terminate the application
//            //    }
//            //    else
//            //        MessageBox.Show("One table exists!!!!");

//            //    // Check if books table exists
//            //    SqlCommand cmdMany = new SqlCommand(this.manyExists, c);
//            //    object resultMany = cmdMany.ExecuteScalar();
//            //    if (resultMany == null)
//            //    {
//            //        MessageBox.Show("Many table does not exist.");
//            //        // Handle accordingly, maybe create the table or terminate the application
//            //    }
//            //    else
//            //        MessageBox.Show("Many table exists!!!");

//            //}
//            //catch (Exception ex)
//            //{
//            //    MessageBox.Show("Error checking table existence: " + ex.Message);
//            //}
//            //finally
//            //{
//            //    c.Close();
//            //}
//            try
//            {
//                using (SqlConnection connection = new SqlConnection(this.connection))
//                {
//                    connection.Open();

//                    // Check if authors table exists
//                    SqlCommand cmdOne = new SqlCommand(this.oneExists, connection);
//                    object resultOne = cmdOne.ExecuteScalar();
//                    if (resultOne == null)
//                    {
//                        MessageBox.Show("One table does not exist.");
//                        // Handle accordingly, maybe create the table or terminate the application
//                    }
//                    else
//                    {
//                        MessageBox.Show("One table exists!");
//                    }

//                    // Check if books table exists
//                    SqlCommand cmdMany = new SqlCommand(this.manyExists, connection);
//                    object resultMany = cmdMany.ExecuteScalar();
//                    if (resultMany == null)
//                    {
//                        MessageBox.Show("Many table does not exist.");
//                        // Handle accordingly, maybe create the table or terminate the application
//                    }
//                    else
//                    {
//                        MessageBox.Show("Many table exists!");
//                    }
//                }
//            }
//            catch (Exception ex)
//            {
//                MessageBox.Show("Error checking table existence: " + ex.Message);
//            }
//        }
//        void CheckData()
//        {
//            try
//            {
//                c = new SqlConnection(this.connection);
//                c.Open();

//                // Check if One table is populated
//                SqlCommand cmdOne = new SqlCommand(this.oneDataExists, c);
//                SqlDataReader readerOne = cmdOne.ExecuteReader();
//                if (readerOne.HasRows)
//                {
//                    MessageBox.Show("One table is populated!");
//                }
//                else
//                {
//                    MessageBox.Show("Authors table is empty.");
//                }
//                readerOne.Close();

//                // Check if Many table is populated
//                SqlCommand cmdMany = new SqlCommand(this.manyDataExists, c);
//                SqlDataReader readerMany = cmdMany.ExecuteReader();
//                if (readerMany.HasRows)
//                {
//                    MessageBox.Show("Many table is populated!");
//                }
//                else
//                {
//                    MessageBox.Show("Books table is empty.");
//                }
//                readerMany.Close();
//            }
//            catch (Exception ex)
//            {
//                MessageBox.Show("Error checking data: " + ex.Message);
//            }
//            finally
//            {
//                c.Close();
//            }
//        }


//    }
//}
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;

namespace prj1
{
    public partial class Form1 : Form
    {
        //SqlConnection connection;
        //SqlDataAdapter daAuthors; //table authors
        //SqlDataAdapter daBooks; //table books
       
        ////BindingSource bsAuthors;
        ////BindingSource bsBooks;
        //SqlCommandBuilder cmdBuilder;
        //string queryAuthors;
        //string queryBooks;
        public static string server = ConfigurationManager.AppSettings.Get("server");
        public static string database = ConfigurationManager.AppSettings.Get("database");
        public static string parentTable = ConfigurationManager.AppSettings.Get("parentTable");
        public static string childTable = ConfigurationManager.AppSettings.Get("childTable");
        public static string parentPrimaryKey = ConfigurationManager.AppSettings.Get("parentPrimaryKey");
        public static string childForeignKey = ConfigurationManager.AppSettings.Get("childForeignKey");
        SqlConnection sqlConnection = new SqlConnection("Data Source = OMG\\MSSQLSERVER01; Initial Catalog = library; Integrated Security = True");
        DataSet ds= new DataSet();
        SqlDataAdapter parentDataAdapter = new SqlDataAdapter();
        SqlDataAdapter childDataAdapter = new SqlDataAdapter();

        BindingSource parentBindingSource = new BindingSource();
        BindingSource childBindingSource = new BindingSource();
        SqlCommandBuilder parentBuilder = new SqlCommandBuilder();
        SqlCommandBuilder childBuilder = new SqlCommandBuilder();
        public Form1()
        {
            InitializeComponent();
        }
        public void populate()
        {
            parentDataAdapter = new SqlDataAdapter("SELECT * FROM " + parentTable, sqlConnection);
            childDataAdapter = new SqlDataAdapter("SELECT * FROM " + childTable, sqlConnection);
            
            SqlCommandBuilder parentBuilder = new SqlCommandBuilder(parentDataAdapter);
            SqlCommandBuilder childBuilder = new SqlCommandBuilder(childDataAdapter);

            parentDataAdapter.Fill(ds, parentTable);
            childDataAdapter.Fill(ds, childTable);

            DataColumn parentPK = ds.Tables[parentTable].Columns[parentPrimaryKey];
            DataColumn childFK = ds.Tables[childTable].Columns[childForeignKey];
            
            DataRelation relation = new DataRelation("fk_parent_child", parentPK, childFK);
            ds.Relations.Add(relation);
            
            parentBindingSource.DataSource = ds;
            parentBindingSource.DataMember = parentTable;

            childBindingSource.DataSource = parentBindingSource;
            childBindingSource.DataMember = "fk_parent_child";
            
            dataGridView1.DataSource = parentBindingSource;
            dataGridView2.DataSource = childBindingSource;
        }

        private string getConnectionString()
        {
            return "Data Source=OMG\\MSSQLSERVER01;Initial Catalog=library;Integrated Security=True";
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            populate();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                //daBooks.Update(ds, "Books");
                parentDataAdapter.Update(ds, parentTable);
                childDataAdapter.Update(ds, childTable);
                MessageBox.Show("Updated with succes!");

            }
            catch (Exception ex)
            {
                MessageBox.Show("An error occurred while updating data: " + ex.Message);
                sqlConnection.Close();
            }

        }
        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}

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
namespace prj1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        SqlDataAdapter daAuthors; //table authors
        SqlDataAdapter daBooks; //table books
        DataSet ds;
        //BindingSource bsAuthors;
        //BindingSource bsBooks;
        SqlCommandBuilder cmdBuilder;
        string queryAuthors;
        string queryBooks;
        public Form1()
        {
            InitializeComponent();
            FillData();
        }
        void FillData() //fill form with data from db
        {
            try { 
            connection = new SqlConnection(getConnectionString());
            queryAuthors = "SELECT * FROM Authors";
            queryBooks = "SELECT * FROM Books";

            daAuthors = new SqlDataAdapter(queryAuthors, connection);
            daBooks = new SqlDataAdapter(queryBooks, connection);
            ds = new DataSet();

                daAuthors.Fill(ds, "Authors");
                daBooks.Fill(ds, "Books");
                
                cmdBuilder = new SqlCommandBuilder(daBooks);
                
                ds.Relations.Add("AuthorsBooks",
                    ds.Tables["Authors"].Columns["author_id"],
                    ds.Tables["Books"].Columns["author_id"]);
                //method1
                //fill data into datagridview
            dataGridView1.DataSource = ds.Tables["Authors"];
            dataGridView2.DataSource = dataGridView1.DataSource;
            dataGridView2.DataMember = "AuthorsBooks";

            

            //insert, update, delete commands via sqlCommandBuilder
            cmdBuilder.GetUpdateCommand();
            }catch (Exception ex)
            {
                MessageBox.Show("An error occurred while filling data: " + ex.Message);
            }

        }
        private string getConnectionString()
        {
            return "Data Source=OMG\\MSSQLSERVER01;Initial Catalog=library;Integrated Security=True";
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daBooks.Update(ds, "Books");
            }
            catch (Exception ex)
            {
                MessageBox.Show("An error occurred while updating data: " + ex.Message);
            }
            
        }
        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}

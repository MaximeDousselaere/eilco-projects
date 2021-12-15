// code behind
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Xml;
using System.Xml.Serialization;
using Newtonsoft.Json;
using System.Text;
using System.IO;

public partial class _Default : Page
{

    string url = "C:\\Users\\Utilisateur\\Documents\\Visual Studio 2017\\Projets\\WebSite1\\WebSite1\\App_Data\\";

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Afficher_Donnees_SQL(object sender, EventArgs e)
    {
        // C:\Users\Utilisateur\Documents\Visual Studio 2017\Projets\WebSite1\WebSite1\App_Data\bdd.mdf

        string PARAMS_INTEROP =
              "Data Source = (LocalDB)\\MSSQLLocalDB;" +
                "AttachDbFilename = "+url+"bdd.mdf;" +
                 "Integrated Security = True";


        SqlConnection connection = new SqlConnection(PARAMS_INTEROP);


        connection.Open();
        Response.Write("<br> INFO : Server version <b>" + connection.ServerVersion+"</b>");
        Response.Write("<br> INFO : Etat de connexion : <b>" + connection.State+ "</b>");
        string TABLE_CONCERNEE = "Table";
        string REQ_SQL = "SELECT[EKP_NUM], [EKP_NOM], [EKP_VILLE], concat(MONTH([EKP_DATE]),'/',YEAR([EKP_DATE])) FROM[" + TABLE_CONCERNEE + "]";

        using (SqlDataAdapter adaptateur = new SqlDataAdapter(
                REQ_SQL, connection))
        {
            //  adaptateur.MissingSchemaAction = MissingSchemaAction.AddWithKey;

            DataTable dt = new DataTable();
            adaptateur.Fill(dt);
            Response.Write("<center> <br>= = = = = = = = = = = = = = = = = = = = = =<br>  ");
            Response.Write("&emsp;&emsp; Affichage du  Contenu la table " + TABLE_CONCERNEE);
            Response.Write("<br> = = = = = = = = = = = = = = = = = = = = = =  ");
            Response.Write("<table cellpadding='3' cellspacing='3' border='3' bordercolor='green'>");
            int i, k = 1;
            foreach (DataRow dr in dt.Rows)
            {
                i = 0;
                // Affichage des valeurs des champs pour s'assurer 
                // de la capture des données via le DataSet 
                Response.Write("<tr>");

                Response.Write("<td align=center> <font size=+1 color=green>&emsp;" + k + "</td>");
                Response.Write("<td align=center> <font size=+1 color=blue>&emsp;" + dr[i].ToString() + " </td>");
                Response.Write("<td align=center> <font size=+1 color=red>&emsp;" + dr[i + 1].ToString() + "</td>");
                Response.Write("<td align=center> <font size=+1 color=purple>&emsp;" + dr[i + 2].ToString() + "</td>");
                Response.Write("<td align=center> <font size=+1 color=yellow>&emsp;" + dr[i + 3].ToString() + "</td>");
                k++;
            }
            Response.Write("<table>");
            Response.Write("<center>");
        }

    }

    /*
      Cette fonction va dans un premier temps aller chercher les données de la table puis va écrire les résultats ligne par ligne dans des objets XML      
    */
    protected void SQL_to_XML(object sender, EventArgs e)
    {
        // C:\Users\Utilisateur\Documents\Visual Studio 2017\Projets\WebSite1\WebSite1\App_Data\bdd.mdf

        string PARAMS_INTEROP =
              "Data Source = (LocalDB)\\MSSQLLocalDB;" +
                "AttachDbFilename = "+url+"bdd.mdf;" +
                 "Integrated Security = True";

        SqlConnection connection = new SqlConnection(PARAMS_INTEROP);

        connection.Open();
        string TABLE_CONCERNEE = "Table";
        string REQ_SQL = "SELECT[EKP_NUM], [EKP_NOM], [EKP_VILLE], concat(MONTH([EKP_DATE]),'/',YEAR([EKP_DATE])) FROM[" + TABLE_CONCERNEE + "]";

        using (SqlDataAdapter adaptateur = new SqlDataAdapter(
                REQ_SQL, connection))
        {
            //  adaptateur.MissingSchemaAction = MissingSchemaAction.AddWithKey;

            DataTable dt = new DataTable();
            adaptateur.Fill(dt);
            // SQL to XML : 
            // Création de fichier XML qui sera initié avec la variable writer de type   XmlWriter
            using (XmlWriter writer = XmlWriter.Create(url+"EQUIPES.xml"))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("EQUIPES");

                foreach (DataRow dr in dt.Rows)
                {
                    writer.WriteStartElement("EQUIPE");
                    writer.WriteElementString("NUMERO", dr[0].ToString());
                    writer.WriteElementString("NOM", dr[1].ToString());
                    writer.WriteElementString("VILLE", dr[2].ToString());
                    writer.WriteElementString("DATE", dr[3].ToString());
                    writer.WriteEndElement();

                }

                writer.WriteEndDocument();
            }
        }
        Response.Write("<br/><br/>INFO : Le fichier généré s'appelle <b>EQUIPES.xml</b>");
    }

    protected void SQL_to_JSON(object sender, EventArgs e)
    {
        SQL_to_XML("", new EventArgs()); // on génère le fichier XML avec la fonction d'avant
        XmlDocument doc = new XmlDocument();
        string xmlString = System.IO.File.ReadAllText(url+"EQUIPES.xml");
        // charger la chaîne dans un fichier de type XmlDocument
        doc.LoadXml(xmlString);
        // sérialisation du contenu XML de doc en format json
        string json = JsonConvert.SerializeXmlNode(doc);
        // chemin d'accès au  fichier json
        string path = @""+url+"EQUIPES.json";
        //exporter les données vers un fichier  json en utilisant TexWriter. 
        using (TextWriter tw = new StreamWriter(path))
        {
            tw.WriteLine(json);
        };
        Response.Write("<br/><br/>INFO : Le fichier généré s'appelle <b>EQUIPES.json</b>");
    }
}
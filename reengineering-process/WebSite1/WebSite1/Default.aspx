<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <br />
    <br />
    <center>

            

        <table>
            <tr>
                <td> Afficher le contenu de la table Table </td>
                <td> 
                    <asp:Button ID="Button1" runat="server" Text="Afficher_Donnees_SQL" Height="40px" OnClick="Afficher_Donnees_SQL" Width="200px" />
                </td>
            </tr>
            <tr>
                <td> Convertir le contenu SQL en XML </td>
                <td>
                    <asp:Button ID="Button2" runat="server" Text="SQL_to_XML" Height="40px" OnClick="SQL_to_XML" Width="200px" />
                </td>
            </tr>
            <tr>
                <td> Convertir le contenu SQL en JSON </td>
                <td>
                    <asp:Button ID="Button3" runat="server" Text="SQL_to_JSON" Height="40px" OnClick="SQL_to_JSON" Width="200px" />
                </td>
            </tr>
        </table>

        <asp:SqlDataSource ID="SqlDataSource1" runat="server"></asp:SqlDataSource>
    </center>
</asp:Content>

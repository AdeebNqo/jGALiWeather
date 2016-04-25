<%@page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.text.*"%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="es_ES"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset=utf-8>
        <title>Concellos - MeteoGalicia</title>
        <meta name="Keywords" content="concellos, ayuntamientos, curto prazo, medio prazo, meteorología, meteoroloxía, predicción,predición" />
        <meta name="Description" content="Predición meteorolóxica terrestre a curto e medio prazo para concellos elaborada por MeteoGalicia" />
        <meta name="robots" content="index, follow">
        <link rel="icon"  type="image/x-icon" href="images/favicon.ico"/>
        <meta property="og:locale" content="es_ES"/>
        <meta property="og:type" content="website"/>
        <meta property="og:title" content="Concellos - MeteoGalicia"/>
        <meta property="og:description" content="Predición meteorolóxica terrestre a curto e medio prazo para concellos elaborada por MeteoGalicia"/>
        <meta property="og:site_name" content="Meteogalicia"/>

        <link rel="stylesheet" type="text/css" href="styles/index.css">
    </head>
    <body>
        <header class="headerPrincipal">
            <h1>jGALiWeather</h1>
        </header>

        <div id="forecast">
            <h1>Council forecast</h1>
        </div>

        <aside>
            <h2>Councils</h2>
            <nav class="menuCities">
                <a class="menuCitiesElement" href="index.jsp">A Coruña</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Ferrol</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Lugo</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Ourense</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Pontevedra</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Santiago de Compostela</a>
                <a class="menuCitiesElement" href="documents/buscar.jsp">Vigo</a>
            </nav>

            <h2 class="anotherCouncil">Search another council</h2>

            <form name="setCouncil" action="setCouncil" method="get">

                <h3>Choose a province:</h3>
                <input type="radio" checked><label>A Coruña</label>
                <input type="radio"><label>Lugo</label>
                <input type="radio"><label>Ourense</label>
                <input type="radio"><label>Pontevedra</label>

                <h3>Choose a council in A Coruña:</h3>
                <select name="council" size="1">
                    <option selected value="Muy facil">Muy fácil</option>
                    <option value="Facil">Fácil</option>
                    <option value="Regular">Regular</option>
                    <option value="Dificil">Dificil</option>
                    <option value="Muy dificil">Muy dificil</option>
                </select>
            </form>
        </aside>

        <main>
            <article>
                <header>

                </header>
            </article>
        </main>

        <footer>

        </footer>
    </body>
</html>

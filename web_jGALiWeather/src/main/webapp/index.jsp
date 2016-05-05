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
        <script type="text/javascript" src="lib/jquery-2.2.3.min.js" ></script>
        <script type="text/javascript" src="lib/servicios.js" ></script>
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
            <nav>
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
                    <h2>Short term forecast for A Coruña</h2>   
               </header>
                
                <table>
                        <thead>
                            <tr>
                                <th>Tabla</th>
                                <th colspan=3>Thursday, 28th April</th>
                                <th colspan=3>Friday, 29th April</th>
                                <th colspan=3>Saturday, 30th April</th>
                                <th colspan=3>Sunday, 1st May</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Period</td>

                                <td>Morning</td>
                                <td>Afternoon</td>
                                <td>Night</td>

                                <td>Morning</td>
                                <td>Afternoon</td>
                                <td>Night</td>

                                <td>Morning</td>
                                <td>Afternoon</td>
                                <td>Night</td>

                                <td>Morning</td>
                                <td>Afternoon</td>
                                <td>Night</td>
                            </tr>

                            <tr>
                                <td>Sky State</td>

                                <td id="skMorning1"></td>
                                <td id="skAfternoon1"></td>
                                <td id="skNight1"></td>

                                <td id="skMorning2"></td>
                                <td id="skAfternoon2"></td>
                                <td id="skNight2"></td>

                                <td id="skMorning3"></td>
                                <td id="skAfternoon3"></td>
                                <td id="skNight3"></td>

                                <td id="skMorning4"></td>
                                <td id="skAfternoon4"></td>
                                <td id="skNight4"></td>
                            </tr>

                            <tr>
                                <td>Wind</td>

                                <td id="windMorning1"></td>
                                <td id="windAfternoon1"></td>
                                <td id="windNight1"></td>

                                <td id="windMorning2"></td>
                                <td id="windAfternoon2"></td>
                                <td id="windNight2"></td>

                                <td id="windMorning3"></td>
                                <td id="windAfternoon3"></td>
                                <td id="windNight3"></td>

                                <td id="windMorning4"></td>
                                <td id="windAfternoon4"></td>
                                <td id="windNight4"></td>
                            </tr>

                            <tr>
                                <td>Rain probability</td>

                                <td>5%</td>
                                <td>5%</td>
                                <td>5%</td>

                                <td>5%</td>
                                <td>5%</td>
                                <td>5%</td>

                                <td>5%</td>
                                <td>5%</td>
                                <td>5%</td>

                                <td>5%</td>
                                <td>5%</td>
                                <td>5%</td>
                            </tr>

                            <tr>
                                <td rowspan=2>Temperatura</td>

                                <td colspan=3>
                                    <div class="min_label">
                                        Min
                                    </div>
                                    <div class="max_label">
                                        Max
                                    </div>
                                </td>

                                <td colspan=3>
                                    <div class="min_label">
                                        Min
                                    </div>
                                    <div class="max_label">
                                        Max
                                    </div>
                                </td>

                                <td colspan=3>
                                    <div class="min_label">
                                        Min
                                    </div>
                                    <div class="max_label">
                                        Max
                                    </div>
                                </td>

                                <td colspan=3>
                                    <div class="min_label">
                                        Min
                                    </div>
                                    <div class="max_label">
                                        Max
                                    </div>
                                </td>
                            </tr>       

                            <tr>
                                <td colspan=3>
                                    <div id="tempMin1" class="min"></div>
                                    <div id="tempMax1" class="max"></div>
                                </td>

                                <td colspan=3>
                                    <div id="tempMin2" class="min"></div>
                                    <div id="tempMax2" class="max"></div>
                                </td>

                                <td colspan=3>
                                    <div id="tempMin3" class="min"></div>
                                    <div id="tempMax3" class="max"></div>
                                </td>

                                <td colspan=3>
                                    <div id="tempMin4" class="min"></div>
                                    <div id="tempMax4" class="max"></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Air Quality</td>

                                <td colspan=3>Green</td>

                                <td colspan=3>Green</td>

                                <td colspan=3>Yellow</td>

                                <td colspan=3>-</td>
                            </tr>
                            
                            <tr>
                                <td>Comment</td>

                                <td id="comment" colspan=12></td>
                            </tr>
                        </tbody>
                    </table>
            </article>
        </main>

        <footer>

        </footer>
    </body>
</html>

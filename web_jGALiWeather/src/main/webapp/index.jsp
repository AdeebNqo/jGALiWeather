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
        <link rel="stylesheet" type="text/css" href="styles/temperatures.css">
        <script type="text/javascript" src="lib/jquery-2.2.3.min.js" ></script>
        <script type="text/javascript" src="lib/servicios.js" ></script>
    </head>

    <body>
        <div id="content">
            <header class="headerPrincipal">
                <h1>jGALiWeather</h1>
            </header>

            <div id="forecast">
                <h1>Council forecast</h1>
            </div>

            <aside>
                <h2>Councils</h2>
                <nav>
                    <ul>
                        <li><a class="menuCitiesElement" href="index.jsp">A Coruña</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Ferrol</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Lugo</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Ourense</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Pontevedra</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Santiago de Compostela</a></li>
                        <li><a class="menuCitiesElement" href="documents/buscar.jsp">Vigo</a></li>
                    </ul>
                </nav>

                <h2 class="anotherCouncil">Search another council</h2>

                <form name="setCouncil" action="setCouncil" method="get">

                    <h3>Choose a province:</h3>
                    <div class="radioCorunaOurense">
                        <input type="radio" name="province" id="coruna" checked><label for="coruna">A Coruña</label><br>
                        <input type="radio" name="province" id="ourense"><label for="ourense">Ourense</label>
                    </div>
                    <div class="radioPontevedraLugo">
                        <input type="radio" name="province" id="lugo"><label for="lugo">Lugo</label><br>
                        <input type="radio" name="province" id="pontevedra"><label for="pontevedra">Pontevedra</label>
                    </div>

                    <div id="chooser">
                        <h3>Choose a council in A Coruña:</h3>
                        <select name="council" size="1">
                            <option selected value="Muy facil">Muy fácil</option>
                            <option value="Facil">Fácil</option>
                            <option value="Regular">Regular</option>
                            <option value="Dificil">Dificil</option>
                            <option value="Muy dificil">Muy dificil</option>
                        </select>
                    </div>
                </form>

                <div class="box">
                    <h4>Useful Links</h4>
                    <a href="http://www.meteogalicia.es/web/predicion/localidades/lendaLoc.action?request_locale=gl">Legend</a>
                </div>
            </aside>

            <main>
                <article>
                    <header>
                        <h2>Short term forecast for A Coruña</h2>   
                    </header>

                    <table>
                        <thead>
                            <tr>
                                <th class="title">Tabla</th>
                                <th class="dayHeader" colspan=3>Thursday, 28th April</th>
                                <th class="dayHeader" colspan=3>Friday, 29th April</th>
                                <th class="dayHeader" colspan=3>Saturday, 30th April</th>
                                <th class="dayHeader" colspan=3>Sunday, 1st May</th>
                            </tr>

                            <tr>
                                <th class="title">Period</th>

                                <th class="periodHeader">Morning</th>
                                <th class="periodHeader">Afternoon</th>
                                <th class="periodHeader">Night</th>

                                <th class="periodHeader">Morning</th>
                                <th class="periodHeader">Afternoon</th>
                                <th class="periodHeader">Night</th>

                                <th class="periodHeader">Morning</th>
                                <th class="periodHeader">Afternoon</th>
                                <th class="periodHeader">Night</th>

                                <th class="periodHeader">Morning</th>
                                <th class="periodHeader">Afternoon</th>
                                <th class="periodHeader">Night</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="title">Sky State</td>

                                <td><img id="skMorning1"></td>
                                <td><img id="skAfternoon1"></td>
                                <td><img id="skNight1"></td>

                                <td><img id="skMorning2"></td>
                                <td><img id="skAfternoon2"></td>
                                <td><img id="skNight2"></td>

                                <td><img id="skMorning3"></td>
                                <td><img id="skAfternoon3"></td>
                                <td><img id="skNight3"></td>

                                <td><img id="skMorning4"></td>
                                <td><img id="skAfternoon4"></td>
                                <td><img id="skNight4"></td>
                            </tr>

                            <tr>
                                <td class="title">Wind</td>

                                <td><img id="windMorning1"></td>
                                <td><img id="windAfternoon1"></td>
                                <td><img id="windNight1"></td>

                                <td><img id="windMorning2"></td>
                                <td><img id="windAfternoon2"></td>
                                <td><img id="windNight2"></td>

                                <td><img id="windMorning3"></td>
                                <td><img id="windAfternoon3"></td>
                                <td><img id="windNight3"></td>

                                <td><img id="windMorning4"></td>
                                <td><img id="windAfternoon4"></td>
                                <td><img id="windNight4"></td>
                            </tr>

                            <tr>
                                <td class="title">Rain probability</td>

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
                                <td class="title" rowspan=2>Temperatura</td>

                                <td class="tempLabels" colspan=3>
                                    <div class="min_label">
                                        MIN
                                    </div>
                                    <div class="max_label">
                                        MAX
                                    </div>
                                </td>

                                <td class="tempLabels" colspan=3>
                                    <div class="min_label">
                                        MIN
                                    </div>
                                    <div class="max_label">
                                        MAX
                                    </div>
                                </td>

                                <td class="tempLabels" colspan=3>
                                    <div class="min_label">
                                        MIN
                                    </div>
                                    <div class="max_label">
                                        MAX
                                    </div>
                                </td>

                                <td class="tempLabels" colspan=3>
                                    <div class="min_label">
                                        MIN
                                    </div>
                                    <div class="max_label">
                                        MAX
                                    </div>
                                </td>
                            </tr>       

                            <tr>
                                <td colspan=3>
                                    <div class="min_temp" id="tempMin1"></div>
                                    <div class="max_temp" id="tempMax1"></div>
                                </td>

                                <td colspan=3>
                                    <div class="min_temp" id="tempMin2"></div>
                                    <div class="max_temp" id="tempMax2"></div>
                                </td>

                                <td colspan=3>
                                    <div class="min_temp" id="tempMin3"></div>
                                    <div class="max_temp" id="tempMax3"></div>
                                </td>

                                <td colspan=3>
                                    <div class="min_temp" id="tempMin4"></div>
                                    <div class="max_temp" id="tempMax4"></div>
                                </td>
                            </tr>

                            <tr>
                                <td class="title">Air Quality</td>

                                <td colspan=3>Green</td>

                                <td colspan=3>Green</td>

                                <td colspan=3>Yellow</td>

                                <td colspan=3>-</td>
                            </tr>

                            <tr>
                                <td class="title">Comment</td>

                                <td id="comment" colspan=12></td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="divNote">
                        <img class="noteImg" width="23" src="images/users2.png" alt="Short Term forecast for councils">
                        <span class="dataSource_title">Data source:</span>
                        <span class="dataSource_text">
                            Forecast shown in this section are made by MeteoGalicia's Operative Forecasting team.		
                        </span>
                    </div>
                    
                </article>
            </main>

            <footer>
                <span class="design">Developed by Diego Iglesias Freire</span><span class="contact">Contact on: <a class="mail" href="mailto:diego.iglesias.freire@rai.usc.es?Subject=jGALiWeather">diego.iglesias.freire@rai.usc.es</a></span>
            </footer>
        </div>
    </body>
</html>

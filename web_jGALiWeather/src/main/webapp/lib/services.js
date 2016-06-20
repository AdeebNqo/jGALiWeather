
//Changes the background color of the temperature labels
function changeColorBackground(t, value) {

    var classList = t.attr('class').split(/\s+/);
    t.removeClass(classList[1]);

    if (value >= 0) {
        t.addClass("t" + value);
    } else {
        t.addClass("m" + Math.abs(value));
    }
}

// Gets the forecast data from the restful service
function getForecastData(id) {
    $.ajax({
        type: "GET",
        url: "jgaliweather_api/v1/meteorologicalData/" + id,
        success: function (data, textStatus, response) {

            $("#tableTitle").text(data.name);

            // SkyState
            $("#skMorning1").attr('src', 'images/meteors/sky/' + data.sky[0] + '.png');
            $("#skAfternoon1").attr('src', 'images/meteors/sky/' + data.sky[1] + '.png');
            $("#skNight1").attr('src', 'images/meteors/sky/' + (data.sky[2] + 100) + '_fondo.png');

            $("#skMorning2").attr('src', 'images/meteors/sky/' + data.sky[3] + '.png');
            $("#skAfternoon2").attr('src', 'images/meteors/sky/' + data.sky[4] + '.png');
            $("#skNight2").attr('src', 'images/meteors/sky/' + (data.sky[5] + 100) + '_fondo.png');

            $("#skMorning3").attr('src', 'images/meteors/sky/' + data.sky[6] + '.png');
            $("#skAfternoon3").attr('src', 'images/meteors/sky/' + data.sky[7] + '.png');
            $("#skNight3").attr('src', 'images/meteors/sky/' + (data.sky[8] + 100) + '_fondo.png');

            $("#skMorning4").attr('src', 'images/meteors/sky/' + data.sky[9] + '.png');
            $("#skAfternoon4").attr('src', 'images/meteors/sky/' + data.sky[10] + '.png');
            $("#skNight4").attr('src', 'images/meteors/sky/' + (data.sky[11] + 100) + '_fondo.png');

            // WIND
            $("#windMorning1").attr('src', 'images/meteors/wind/' + data.wind[0] + '.png');
            $("#windAfternoon1").attr('src', 'images/meteors/wind/' + data.wind[1] + '.png');
            $("#windNight1").attr('src', 'images/meteors/wind/' + data.wind[2] + '.png');

            $("#windMorning2").attr('src', 'images/meteors/wind/' + data.wind[3] + '.png');
            $("#windAfternoon2").attr('src', 'images/meteors/wind/' + data.wind[4] + '.png');
            $("#windNight2").attr('src', 'images/meteors/wind/' + data.wind[5] + '.png');

            $("#windMorning3").attr('src', 'images/meteors/wind/' + data.wind[6] + '.png');
            $("#windAfternoon3").attr('src', 'images/meteors/wind/' + data.wind[7] + '.png');
            $("#windNight3").attr('src', 'images/meteors/wind/' + data.wind[8] + '.png');

            $("#windMorning4").attr('src', 'images/meteors/wind/' + data.wind[9] + '.png');
            $("#windAfternoon4").attr('src', 'images/meteors/wind/' + data.wind[10] + '.png');
            $("#windNight4").attr('src', 'images/meteors/wind/' + data.wind[11] + '.png');

            //Temperature
            $("#tempMin1").text(data.temp[0] + "\xB0");
            $("#tempMax1").text(data.temp[1] + "\xB0");
            changeColorBackground($("#tempMin1"), data.temp[0]);
            changeColorBackground($("#tempMax1"), data.temp[1]);

            $("#tempMin2").text(data.temp[2] + "\xB0");
            $("#tempMax2").text(data.temp[3] + "\xB0");
            changeColorBackground($("#tempMin2"), data.temp[2]);
            changeColorBackground($("#tempMax2"), data.temp[3]);

            $("#tempMin3").text(data.temp[4] + "\xB0");
            $("#tempMax3").text(data.temp[5] + "\xB0");
            changeColorBackground($("#tempMin3"), data.temp[4]);
            changeColorBackground($("#tempMax3"), data.temp[5]);

            $("#tempMin4").text(data.temp[6] + "\xB0");
            $("#tempMax4").text(data.temp[7] + "\xB0");
            changeColorBackground($("#tempMin4"), data.temp[6]);
            changeColorBackground($("#tempMax4"), data.temp[7]);

            //Comment
            if (data.comment !== "") {
                $("#comment").text(data.comment);
            } else {
                $("#comment").text("There's no comments avaliable");
            }
            if (document.documentElement.clientWidth <= 1280) {
                $("#collapseTables").collapsible("option", "collapsed", false);
            }
        },
        error: function (response, textStatus, errorThrown) {
            alert("Municipio no encontrado");
        }
    });
}

// It gets the address of specific coordinates from the geocoding API of Google
function getAddress(lat, lng) {
    $.ajax({
        type: "GET",
        url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyAEauii-d22ov-FYPdg6EgIe-4GWA30qLQ",
        success: function (data, textStatus, response) {

            var obj;
            var name = data.results[0].address_components[2].long_name;

            if (data.results[0].address_components[4].long_name === "Ourense") {

                obj = JSON.parse(concOurense);

                $.each(obj.items, function (i, item) {
                    if (item.nome === name) {
                        getForecastData(item.idConc);
                    }
                });

            } else if (data.results[0].address_components[4].long_name === "Pontevedra") {

                obj = JSON.parse(concPontevedra);

                $.each(obj.items, function (i, item) {
                    if (item.nome === name) {
                        getForecastData(item.idConc);
                    }
                });

            } else if (data.results[0].address_components[4].long_name === "Lugo") {

                obj = JSON.parse(concLugo);

                $.each(obj.items, function (i, item) {
                    if (item.nome === name) {
                        getForecastData(item.idConc);
                    }
                });

            } else if (data.results[0].address_components[4].short_name === "C") {

                obj = JSON.parse(concCoruna);

                if (name === "La Coruña") {
                    name = "A Coruña";
                }

                $.each(obj.items, function (i, item) {
                    if (item.nome === name) {
                        getForecastData(item.idConc);
                    }
                });
            } else {
                getForecastData("15030");
            }

        },
        error: function (response, textStatus, errorThrown) {
            getForecastData("15030");
        }
    });
}

function changeCouncil(id) {
    $(".modal").fadeIn(0);
    getForecastData(id);
    if (document.documentElement.clientWidth <= 1280) {
        $("html, body").animate({scrollTop: $("#collapseTables").offset().top}, "slow");
    }
    $(".modal").fadeOut("slow");
}

//Function called when detects a change in the select list
function selectChange() {
    changeCouncil($("#selectCouncil option:selected").val());
}

//Calls the getter when the page loads
$(function () {

    $(document).ready(function () {
        if (document.documentElement.clientWidth <= 1280) {
            var script = document.createElement('script');
            script.src = 'lib/jquery.mobile-1.4.5.min.js';
            script.type = 'text/javascript';
            script.id = 'jqueryMobile';
            document.getElementsByTagName('head')[0].appendChild(script);
        }
        
        var date = moment().tz('Europe/Madrid');

        $("#dayHeader1").text(date.format('dddd, MMMM Do'));
        $("#dayHeader2").text(date.add(1, 'days').format('dddd, MMMM Do'));
        $("#dayHeader3").text(date.add(1, 'days').format('dddd, MMMM Do'));
        $("#dayHeader4").text(date.add(1, 'days').format('dddd, MMMM Do'));

        var succes = false;
        if ('geolocation' in navigator) {
            navigator.geolocation.getCurrentPosition(function (position) {
                getAddress(position.coords.latitude, position.coords.longitude);
                succes = true;
            }, function (error) {
                if (!succes) {
                    getForecastData("15030");
                }
            },
                    {enableHighAccuracy: true, timeout: 5000, maximumAge: 0});
        } else {
            getForecastData("15030");
        }

        $(".modal").fadeOut("slow");
    });
});
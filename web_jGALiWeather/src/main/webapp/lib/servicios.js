function changeColorBackground(t, value) {
    if (value >= 0) {
        t.addClass("t" + value);
    } else {
        t.addClass("m" + Math.abs(value));
    }
}

$(function () {
    window.onload = function () {

        $.ajax({
            type: "GET",
            url: "jgaliweather_api/v1/meteorologicalData/27027",
            success: function (data, textStatus, response) {

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
                $("#tempMin1").append(data.temp[0] + "&deg;"); 
                $("#tempMax1").append(data.temp[1] + "&deg;");
                changeColorBackground($("#tempMin1"), data.temp[0]);
                changeColorBackground($("#tempMax1"), data.temp[1]);

                $("#tempMin2").append(data.temp[2] + "&deg;");
                $("#tempMax2").append(data.temp[3] + "&deg;");
                changeColorBackground($("#tempMin2"), data.temp[2]);
                changeColorBackground($("#tempMax2"), data.temp[3]);

                $("#tempMin3").append(data.temp[4] + "&deg;");
                $("#tempMax3").append(data.temp[5] + "&deg;");
                changeColorBackground($("#tempMin3"), data.temp[4]);
                changeColorBackground($("#tempMax3"), data.temp[5]);

                $("#tempMin4").append(data.temp[6] + "&deg;");
                $("#tempMax4").append(data.temp[7] + "&deg;");
                changeColorBackground($("#tempMin4"), data.temp[6]);
                changeColorBackground($("#tempMax4"), data.temp[7]);

                //Comment
                $("#comment").append(data.comment);

            },
            error: function (response, textStatus, errorThrown) {
                $("#comment").append("ERROR: " + errorThrown);
            }
        });
    };
});
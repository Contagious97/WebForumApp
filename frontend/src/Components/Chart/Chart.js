import jQuery from "jquery"
import $ from "jquery"
import {Chart, registerables} from 'chart.js';
import React, {useRef, useEffect, useState} from "react";
import {defined} from "chart.js/helpers";
import './Chart.css'

export default function Charts() {
    let myChart;


    var config = {

        type: 'line',
        data: {
            labels: ["June", "July", "August", "September", "October", "November", "December"],
            datasets: [{
                label: "company1",
                data: [1, 1, 2, 3, 4, 5, 6],
                fill: true,
                borderColor: "purple",
                backgroundColor: "purple"
            }, {
                label: "company2",
                data: [1, 2, 4, 8, 3, 2, 4],
                fill: true,
                borderColor: "green",
                backgroundColor: "green"
            }
            ]

        },
        options: {
            responsive: false,
            plugins: {
                title: {
                    display: true,
                    text: 'Custom Chart Title'
                }
            }
        }
    };


    $("#line").unbind('click').click(function () {
        console.log("inside lineListener")
        change('line');
    });

    $("#bar").unbind('click').click(function () {
        console.log("inside barListener")
        change('bar');
    });

    var count = 0

    function change(newType) {
        // var ctx = document.getElementById("canvas").getContext("2d");
        const canvas = document.getElementById('canvas');
        const ctx = canvas.getContext('2d');


        // Remove the old chart and all its event handles

        if (myChart) {
            console.log("chart is getting destroyed" + count);
            count = count + 1;
            myChart.destroy();
        }

        // Chart.js modifies the object you pass in. Pass a copy of the object so we can use the original object later
        console.log("before change " + myChart)
        var temp = jQuery.extend(true, {}, config);

        temp.type = newType;
        myChart = new Chart(ctx, temp);
        myChart.resize(600, 600)
        myChart.options.plugins.legend.position = 'top';
        myChart.options.plugins.title.text = globalTitle;
        myChart.update();

        console.log("after change " + myChart)
    }
    var globalTitle;
    function changeName(){
        console.log("inside change name");
        var title = document.getElementById("title").value;
        console.log(title);
        myChart.options.plugins.title.text = title;
        myChart.update();
        globalTitle = title;
    }

    // document.getElementById("myBtn").click();

    return (

        <div className={"chartPosition"}>
            <button id='line'>line</button>
            <button id='bar'>bar</button>
            <canvas id="canvas"></canvas>
            <div>
                <button type="submit" form="form1" value="Submit" onClick={changeName}>Submit</button>
                <label className={"pad"} htmlFor="fname"> Enter title:</label>
                <input type="text" id="title" name="title" color={"white"}/>
            </div>



        </div>


    )

}




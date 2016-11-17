// dimensions
    var margin = {top: 20, right: 20, bottom: 70, left: 40},
            width = 600 - margin.left - margin.right,
            height = 300 - margin.top - margin.bottom;
    // ranges
    var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);
    var y = d3.scale.linear().range([height, 0]);
    // axis
    var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom")
    var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left")
            .ticks(10);

    var svg = d3.select("body").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform",
                    "translate(" + margin.left + "," + margin.top + ")");


    var jsonData = [
                     {
                       "word": "President",
                       "Freq": 20
                     },
                     {
                       "word" : "Election",
                       "Freq": 12
                     },
                     {
                       "word" : "Hillary",
                       "Freq": 47
                     },
                     {
                       "word" : "Donald",
                       "Freq": 42
                     },
                     {
                       "word" : "Democrat",
                       "Freq" : 35
                     },
                     {
                       "word" : "Republican",
                       "Freq" : 32
                     },
                     {
                       "word" : "2016",
                       "Freq" : 14
                     },
                     {
                       "word" : "Vote",
                       "Freq" : 50
                     },
                     {
                       "word" : "Corrupt",
                       "Freq" : 17
                     },
                     {
                       "word" : "Rigged",
                       "Freq" : 5
                     }
                   ];

        var message = [${greeting.content}];
        console.log(message);

        jsonData.forEach(function(d) {
            d.word = d.word;
            d.Freq = +d.Freq;
        });

        x.domain(jsonData.map(function(d) { return d.word; }));
        y.domain([0, d3.max(jsonData, function(d) { return d.Freq; })]);

        // axis
        svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .selectAll("text")
                .style("text-anchor", "end")
                .attr("dx", "-.8em")
                .attr("dy", "-.55em")
                .attr("transform", "rotate(-90)" );
        svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 5)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Frequency of Word");
        // Add bar chart
        svg.selectAll("bar")
                .data(jsonData)
                .enter().append("rect")
                .attr("class", "bar")
                .attr("x", function(d) { return x(d.word); })
                .attr("width", x.rangeBand())
                .attr("y", function(d) { return y(d.Freq); })
                .attr("height", function(d) { return height - y(d.Freq); });

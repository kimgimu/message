
$(document).ready(function() {
    $("#searchButton").click(function() {
        var searchQuery = $("#searchInput").val();
        if (searchQuery.trim() !== "") {
            var apiUrl = 'https://openapi.naver.com/v1/search/news?query=' + encodeURIComponent(searchQuery);

            $.ajax({
                url: apiUrl,
                headers: {
                    'X-Naver-Client-Id': 'JeoZb70w2v35nvvPqzLN',
                    'X-Naver-Client-Secret': 'e6EN3rbnqs'
                },
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    displayResults(data);
                },
                error: function(xhr, status, error) {
                    console.log('Error:', status);
                }
            });
        }
    });

    function displayResults(data) {
        var resultsDiv = $("#searchResults");
        resultsDiv.empty();

        if (data.items && data.items.length > 0) {
            data.items.forEach(function(item) {
                var title = item.title;
                var link = item.link;

                var resultHtml = '<div><h3><a href="' + link + '">' + title + '</a></h3></div>';
                resultsDiv.append(resultHtml);
            });
        } else {
            resultsDiv.append('<p>No results found.</p>');
        }
    }
});



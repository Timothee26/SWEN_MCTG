echo "15) stats"
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and user stats"
echo .
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo "Should return HTTP 200 - and user stats"
echo .
echo .

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

# --------------------------------------------------
echo "16) scoreboard"
curl -i -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and the scoreboard"
echo .
echo .
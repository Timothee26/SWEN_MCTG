echo "19) scoreboard"
curl -i -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and the changed scoreboard"
echo .
echo .

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

# --------------------------------------------------
echo "20) trade"
echo "check trading deals"
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and an empty list"
echo .
echo create trading deal
curl -i -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Type\": \"monster\", \"MinimumDamage\": 15}"
echo "Should return HTTP 201"
echo .

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

echo "check trading deals"
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and the trading deal"
echo .
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer altenhof-mtcgToken"
echo "Should return HTTP 200 - and the trading deal"
echo .

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

echo "delete trading deals"
curl -i -X DELETE http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 2xx"
echo .
echo .

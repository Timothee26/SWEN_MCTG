cho "7) acquire newly created packages altenhof"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo "Should return HTTP 201"
echo .
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo "Should return HTTP 201"
echo .
echo "should fail (no money):"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo "Should return HTTP 4xx - Not enough money"
echo .
echo .

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

# --------------------------------------------------
echo "8) show all acquired cards kienboec"
curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and a list of all cards"
echo "should fail (no token):"
curl -i -X GET http://localhost:10001/cards
echo "Should return HTTP 4xx - Unauthorized"
echo .
echo .

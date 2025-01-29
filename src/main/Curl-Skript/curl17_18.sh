echo "17) battle"
curl -i -X POST http://localhost:10001/battles --header "Authorization: Bearer kienboec-mtcgToken" &
curl -i -X POST http://localhost:10001/battles --header "Authorization: Bearer altenhof-mtcgToken" &
wait

if [ $pauseFlag -eq 1 ]; then read -p "Press enter to continue..."; fi

# --------------------------------------------------
echo "18) Stats"
echo "kienboec"
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo "Should return HTTP 200 - and changed user stats"
echo .
echo altenhof
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo "Should return HTTP 200 - and changed user stats"
echo .
echo .
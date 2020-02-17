import requests
import linecache
import json


def Test1():
    url = "https://tripadvisor1.p.rapidapi.com/flights/create-session"

    querystring = {"currency": "USD", "ta": "1", "ts": "0", "c": "0", "d1": "LAX", "o1": "NYC", "dd1": "2020-05-20"}
    headers = {
        'x-rapidapi-host': "tripadvisor1.p.rapidapi.com",
        'x-rapidapi-key': "5fdeb91092msh41473e4a47d634bp112fe0jsncc889b135197"
    }
    response = requests.request("GET", url, headers=headers, params=querystring)

    response_dict1 = response.json();
    sidval = response_dict1['search_params']['sid']

    url = "https://tripadvisor1.p.rapidapi.com/flights/poll"

    querystring = {"currency": "USD", "ns": "NON_STOP%2CONE_STOP", "so": "PRICE", "sid": sidval}
    headers = {
        'x-rapidapi-host': "tripadvisor1.p.rapidapi.com",
        'x-rapidapi-key': "5fdeb91092msh41473e4a47d634bp112fe0jsncc889b135197"
    }
    response = requests.request("GET", url, headers=headers, params=querystring)

    response_dict = response.json()

    with open('C:\Test1\itineriries.json', 'w',
              encoding='utf-8') as fs:
        json.dump(response_dict['itineraries'], fs)

    json_data = open('C:\Test1\itineriries.json',
                     encoding='utf-8').read()
    data = json.loads(json_data)

    i = 0
    price_info = ""
    flight_info = ""
    itinerary_info = ""

    for item in data:
        for price in data[i]['l']:
            price_info += str(i) + ' ' + str(price['pr']['p']) + ' ' + str(price['pr']['f']) + ' ' + str(price['s']) + '\n'
        for flight in data[i]['f']:
            for info in flight['l']:
                flight_info += str(i) + ' ' + info['m'] + ' ' + info['f'] + ' ' + info['da'] + ' ' + info['aa'] + ' ' + \
                               info['dd'] + ' ' + info['ad'] + '\n'
        i += 1

    result = price_info + flight_info
    print(result)
    return result


if __name__ == '__main__':
    Test1()
# VirtusLab-intership
VirtusLab internship recruitment task

## Candidate: Bartosz Włodarski

## 15 % discount
There are two new discounts:
* FifteenPercentDiscount - single discount which can be apply only before TenPercentDiscount
* FifteenAndTenPercentDiscount - both discounts (15 % discount first)

## Endopoints using Spring Boot
Endpoints get info of basket/products and return receipt with specified discounts applied.
There are two types of endpoints:
* basket/{discountType} - in body should be passed Basket in such JSON format 


        {
          "products": [
          {
          "name": "Milk",
          "type": "DAIRY",
          "price": 2.7
          },
          {
          "name": "Bread",
          "type": "GRAINS",
          "price": 5
          }
          ]
        }


* products/{discountType} - in body should be passed product names from ProductDb with quantity in such JSON format


        {
        "Milk": 50,
        "Cereals": 3,
        "Nothing": 3
        }

Both types response with Receipt in format down below

        {
        "entries": [
        {
        "product": {
        "name": "Cereals",
        "type": "GRAINS",
        "price": 8
        },
        "quantity": 3,
        "totalPrice": 24
        },
        {
        "product": {
        "name": "Milk",
        "type": "DAIRY",
        "price": 2.7
        },
        "quantity": 50,
        "totalPrice": 135.0
        }
        ],
        "discounts": [
        "FifteenPercentDiscount",
        "TenPercentDiscount"
        ],
        "totalPrice": 121.6350
        }

### discountTypes
* fifteenAndTenDiscount
* fifteenDiscount
* tenDiscount
* noneDiscount

## Tests
New tests check discounts and created endpoints 


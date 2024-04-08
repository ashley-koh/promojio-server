# User Endpoints

Code found in `/controllers/UserController.java`

### Register New User [POST] `/user/register`

Creates a new user based on `username`, `password` and `name` (optional).

#### Parameters

| Name       | Type   | Section | Remarks  |
|------------|--------|---------|----------|
| `username` | String | body    | unique   |
| `password` | String | body    | hashed   |
| `name`     | String | body    | optional |


#### Example JSON Request

```json
{
    "name": "John Doe",
    "username": "johndoe",
    "password": "some_password_hash"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### User Login [POST] `/user/login`

Authenticate users based on their credentials. Returns their user object.

#### Parameters

| Name       | Type   | Section | Remarks |
|------------|--------|---------|---------|
| `username` | String | body    | _none_  |
| `password` | String | body    | _none_  |


#### Example JSON Request

```json
{
    "username": "johndoe",
    "password": "some_password_hash"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### Get User by ID [GET] `/user/{id}`

Get a user by their database id. Replace `{id}` with the id of the user when calling this endpoint.

#### Parameters

| Name | Type   | Section | Remarks |
|------|--------|---------|---------|
| `id` | String | Path    | _none_  |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### Update User Name [PATCH] `/user/{id}/update/details`

Updates the details of the user. Currently, that is only the `name` field. Remember to set the headers of the request.

#### Parameters

| Name       | Type   | Section  | Remarks |
|------------|--------|----------|--------|
| `id`       | String | _path_   | _none_ |
| `username` | String | _header_ | _none_  |
| `password` | String | _header_ | _none_  |
| `name`     | String | _body_   | _none_  |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/details
```

#### Example JSON Request

```json
{
    "name": "Sam Saltman"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Update User Points [PATCH] `/user/{id}/update/points`

Updates the `points` of the user, not to be confused with `tierPoints`. Remember to set the headers of the request.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |
| `username` | String  | _header_ | _none_         |
| `password` | String  | _header_ | _none_         |
| `points`   | Integer | _body_   | zero and above |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/points
```

#### Example JSON Request

```json
{
    "points": 42
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Update User Tier Points [PATCH] `/user/{id}/update/tierpoints`

Updates the `tierPoints`. Remember to set the headers of the request.

#### Parameters

| Name         | Type    | Section  | Remarks        |
|--------------|---------|----------|----------------|
| `id`         | String  | _path_   | _none_         |
| `username`   | String  | _header_ | _none_         |
| `password`   | String  | _header_ | _none_         |
| `tierPoints` | Integer | _body_   | zero and above |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/tierpoints
```

#### Example JSON Request

```json
{
    "tierPoints": 24
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Update User Tier Points [POST] `/user/{id}/add/promo`

Attaches a promo to user based on the promo id.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |
| `username` | String  | _header_ | _none_         |
| `password` | String  | _header_ | _none_         |
| `promoId`  | Integer | _body_   | zero and above |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/add/promo
```

#### Example JSON Request

```json
{
    "promoId": "660ba422f8d36a3ba5b419ce"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

#### Delete User [DELETE] `/user/{id}`

Delete a User based on their ID.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35
```

#### Example JSON Response (Success)
```json
{
    "data": null,
    "status": "success"
}
```
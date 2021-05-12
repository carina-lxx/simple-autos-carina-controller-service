package com.echo.cars;

public class AutosControllerTests {
    // GET: /api/autos

    // GET:  /api/autos   returns a lit of all autos
    // GET:  /api/autos   returns 204 no autos found
    // GET:  /api/autos?color=RED   returns all red cars
    // GET:  /api/autos?make=Ford   returns all Ford
    // GET:  /api/autos?make=Ford&color=GREEN   returns green ford


    // POST: /api/autos
        // /api/autos  returns created automobile
        // /api/autos  returns error message due to bad request 400

    // GET: /api/autos/{vin}
        // /api/autos/{vin} returns the requested auto
        // /api/autos/{vin} returns NoContent 204 auto not found

    // PATCH: /api/autos{vin}
        // /api/autos{vin} returns patched automobile
        // /api/autos{vin} returns NoContent auto not found
        // /api/autos{vin} returns 400 bad request (no payload, no changes, or already done)

    // DELETE: /api/autos/{vin}
        // /api/autos/{vin}  Returns 202, deleted request accepted
        // /api/autos/{vin} Returns 204, vehicle not found
}

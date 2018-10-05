'use strict';

describe('Controller Tests', function() {

    describe('Purchase Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPurchase, MockDelivery, MockProduct, MockSupplier, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPurchase = jasmine.createSpy('MockPurchase');
            MockDelivery = jasmine.createSpy('MockDelivery');
            MockProduct = jasmine.createSpy('MockProduct');
            MockSupplier = jasmine.createSpy('MockSupplier');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Purchase': MockPurchase,
                'Delivery': MockDelivery,
                'Product': MockProduct,
                'Supplier': MockSupplier,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PurchaseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'artemisApp:purchaseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

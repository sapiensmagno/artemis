'use strict';

describe('Controller Tests', function() {

    describe('Delivery Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDelivery, MockDeliveryStatus, MockPurchase;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDelivery = jasmine.createSpy('MockDelivery');
            MockDeliveryStatus = jasmine.createSpy('MockDeliveryStatus');
            MockPurchase = jasmine.createSpy('MockPurchase');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Delivery': MockDelivery,
                'DeliveryStatus': MockDeliveryStatus,
                'Purchase': MockPurchase
            };
            createController = function() {
                $injector.get('$controller')("DeliveryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'artemisApp:deliveryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

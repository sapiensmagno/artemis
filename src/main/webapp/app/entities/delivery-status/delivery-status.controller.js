(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryStatusController', DeliveryStatusController);

    DeliveryStatusController.$inject = ['DeliveryStatus'];

    function DeliveryStatusController(DeliveryStatus) {

        var vm = this;

        vm.deliveryStatuses = [];

        loadAll();

        function loadAll() {
            DeliveryStatus.query(function(result) {
                vm.deliveryStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();

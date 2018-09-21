(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('SpecialOfferDialogController', SpecialOfferDialogController);

    SpecialOfferDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SpecialOffer', 'Product'];

    function SpecialOfferDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SpecialOffer, Product) {
        var vm = this;

        vm.specialOffer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.specialOffer.id !== null) {
                SpecialOffer.update(vm.specialOffer, onSaveSuccess, onSaveError);
            } else {
                SpecialOffer.save(vm.specialOffer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artemisApp:specialOfferUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

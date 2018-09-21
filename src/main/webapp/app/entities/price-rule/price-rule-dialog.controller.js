(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PriceRuleDialogController', PriceRuleDialogController);

    PriceRuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PriceRule', 'Product'];

    function PriceRuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PriceRule, Product) {
        var vm = this;

        vm.priceRule = entity;
        vm.clear = clear;
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
            if (vm.priceRule.id !== null) {
                PriceRule.update(vm.priceRule, onSaveSuccess, onSaveError);
            } else {
                PriceRule.save(vm.priceRule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artemisApp:priceRuleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
